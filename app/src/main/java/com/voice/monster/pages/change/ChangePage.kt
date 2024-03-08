package com.voice.monster.pages.change

import android.media.MediaPlayer
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.demon.fmodsound.FmodSound
import com.voice.monster.R
import com.voice.monster.databinding.ChangerPageBinding
import com.voice.monster.effect.Normal
import com.voice.monster.ext.formatHMS
import com.voice.monster.ext.formatMS
import com.voice.monster.ext.getModFilePath
import com.voice.monster.ext.getRecordFilePath
import com.voice.monster.ext.launchIO
import com.voice.monster.ext.showToast
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fmod.FMOD

class ChangePage : BasePage<ChangerPageBinding>() {

  override fun initBinding() = ChangerPageBinding.inflate(layoutInflater)
  private lateinit var filePath: String
  private var selEffect = FmodSound.MODE_NORMAL
  private var isSaveSuccess = false
  private var savedPath = ""
  private var seconds = 0
  private var isPlaying = false
  private var currentSeconds = 0
  private var timerJob: Job? = null

  override fun onBindingFinish() {
    FMOD.init(this)
    filePath = intent.getStringExtra("FilePath") ?: ""
    seconds = intent.getIntExtra("Seconds", 10)
    binding.effectRv.adapter = EffectAdapter(this) { effect ->
      selEffect = effect.id
    }
    binding.endTimeTv.text = seconds.formatMS()
    binding.effectRv.layoutManager = GridLayoutManager(this, 3)
    binding.backIv.singleClick { finish() }
    binding.toggleIv.singleClick {
//      showToast("Play : $filePath $selEffect")
      toggle()
    }
    binding.submitTv.singleClick {
      if (isSaveSuccess) {
        finish()
        return@singleClick
      }
      SaveFileDialog(this) { fileName, dialog ->
        lifecycleScope.launchIO {
          FmodSound.saveSoundAsync(
            filePath,
            selEffect,
            getModFilePath(fileName),
            object : FmodSound.ISaveSoundListener {
              override fun onFinish(path: String, savePath: String, type: Int) {
                runOnUiThread {
                  isSaveSuccess = true
                  savedPath = savePath
                  setSaveFile(fileName)
                  Log.d("Saved", "origin file path : $filePath save mod path $savePath")
                  dialog.dismiss()
                }
              }

              override fun onError(msg: String?) {
                runOnUiThread {
                  dialog.dismiss()
                }
              }
            })
        }
      }.show()
    }

  }

  private fun toggle() {
    if (isPlaying) {
      FmodSound.stopPlay()
      stopTimer()
      isPlaying = false
      binding.toggleIv.setImageResource(R.mipmap.icon_resume)
    } else {
      isPlaying = true
      startTimer()
      binding.toggleIv.setImageResource(R.mipmap.icon_pause)
      lifecycleScope.launchIO {
        FmodSound.playSound(if (isSaveSuccess) savedPath else filePath, selEffect)
      }
    }
  }

  private fun stopTimer() {
    timerJob?.cancel()
    currentSeconds = 0
    isPlaying = false
    binding.startTimeTv.text = currentSeconds.formatMS()
    binding.voicePb.progress = 0
  }

  private fun startTimer() {
    timerJob = lifecycleScope.launch {
      while (isPlaying) {
        currentSeconds += 1
        binding.startTimeTv.text = currentSeconds.formatMS()
        binding.voicePb.progress = ((currentSeconds.toDouble() / seconds.toDouble()) * 100).toInt()
        if (currentSeconds == seconds) {
          stopTimer()
        }
        delay(1000L)
      }
    }
  }

  private fun setSaveFile(fileName: String) {
    isSaveSuccess = true
    binding.fileNameTv.text = fileName
    binding.effectRv.isVisible = false
    binding.fileNameTv.isVisible = true
    binding.successIv.isVisible = true
    binding.successTv.isVisible = true

  }

  override fun onDestroy() {
    super.onDestroy()
    FMOD.close()
    stopTimer()
  }
}