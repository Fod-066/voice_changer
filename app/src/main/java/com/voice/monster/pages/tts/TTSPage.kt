package com.voice.monster.pages.tts

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.voice.monster.R
import com.voice.monster.databinding.TtsPageBinding
import com.voice.monster.ext.getRecordFilePath
import com.voice.monster.ext.showToast
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import com.voice.monster.pages.change.ChangePage
import java.io.File
import java.io.IOException
import java.util.Locale

class TTSPage : BasePage<TtsPageBinding>(), TextToSpeech.OnInitListener {

  private var isReady = false
  private var isSupport = false

  private lateinit var textToSpeech: TextToSpeech

  override fun initBinding(): TtsPageBinding = TtsPageBinding.inflate(layoutInflater)
  override fun onBindingFinish() {
    textToSpeech = TextToSpeech(this, this)
    binding.backIv.singleClick { finish() }
    binding.textEdit.addTextChangedListener { text: Editable? ->
      isReady = !text.isNullOrEmpty()
      updateReady()
    }
    binding.nextTv.singleClick {
      Log.d("TTS", "$isSupport $isReady")
      if (isSupport && isReady) {
        textToSpeech(binding.textEdit.text.toString())
      }
    }
  }

  private fun updateReady() {
    binding.nextTv.setBackgroundResource(if (isReady) R.drawable.shape_conner_22_g else R.drawable.shape_conner_22)
    binding.nextTv.setTextColor(if (isReady) Color.parseColor("#ffffff") else Color.parseColor("#FFA5A5A5"))
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = textToSpeech.setLanguage(Locale.ENGLISH)
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        showToast("Voice is not supported")
        isSupport = false
      } else {
        isSupport = true
      }
    } else {
      showToast("The device does not support $status")
      isSupport = false
    }
  }

  private fun textToSpeech(text: String) {
    val outputFile = File(getRecordFilePath(1))
    val result = textToSpeech.synthesizeToFile(text, null, outputFile, "tts_output")
    if (result != TextToSpeech.SUCCESS) {
      showToast("Conversion failed. Please try again later.")
      return
    }
    startActivity(Intent(this, ChangePage::class.java).apply {
      putExtra("FilePath", outputFile.path)
      putExtra("Seconds", getAudioFileDuration(outputFile.path))
    })
    finish()
  }

  fun getAudioFileDuration(filePath: String): Int {
    val mediaPlayer = MediaPlayer()
    try {
      mediaPlayer.setDataSource(filePath)
      mediaPlayer.prepare()
      return mediaPlayer.duration / 1000
    } catch (e: IOException) {
      e.printStackTrace()
    } finally {
      mediaPlayer.release()
    }
    return 0
  }

}