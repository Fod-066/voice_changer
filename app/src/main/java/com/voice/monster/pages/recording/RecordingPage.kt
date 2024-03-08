package com.voice.monster.pages.recording

import android.content.Intent
import android.os.SystemClock
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.voice.monster.databinding.RecordingPageBinding
import com.voice.monster.ext.formatHMS
import com.voice.monster.ext.getRecordFilePath
import com.voice.monster.ext.showToast
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import com.voice.monster.pages.change.ChangePage
import com.voice.monster.record.RecordingService
import kotlinx.coroutines.Job
import java.io.File

class RecordingPage : BasePage<RecordingPageBinding>() {

  override fun initBinding() = RecordingPageBinding.inflate(layoutInflater)

  private var isRecording = false
  private var intent: Intent? = null
  private var filePath: String = ""
  private var hintTimerJob: Job? = null

  private val vm = RecordingViewModel()

  override fun onBindingFinish() {
    binding.backIv.singleClick { onBack() }
    binding.stopIv.isVisible = true
    binding.stopIv.singleClick { stopRecording(true) }
    vm.perStartRecording()
    observeLiveData()
  }

  private fun observeLiveData() {
    vm.timerCount.observe(this) {
      binding.infoTv.text = "Record at least $it seconds"
      if (it <= 0) {
        startRecording()
      }
    }
    vm.recordingTimerCount.observe(this) {
      binding.timerTv.text = it.formatHMS()
    }
  }

  private fun startRecording() {
    if (isRecording) return
    isRecording = true
    vm.startRecordingTimer()
    intent = Intent(this, RecordingService::class.java)
    filePath = getRecordFilePath(1)
    intent?.putExtra("FilePath", filePath)
    intent?.putExtra("Type", 1)
    binding.stopIv.isVisible = true
    showToast("Start recording...")
    startService(intent)
  }

  private fun stopRecording(needGoChange: Boolean) {
    if (!isRecording) return
    isRecording = false
    vm.stopRecordingTimer()
//    showToast("Recording finish... $filePath ${File(filePath).exists()}")
    stopService(intent)
    if (needGoChange) {
      startActivity(Intent(this, ChangePage::class.java).apply {
        putExtra("FilePath", filePath)
        putExtra("Seconds", vm.recordingTimerCount.value)
      })
      finish()
    }
  }


  override fun onDestroy() {
    super.onDestroy()
    vm.dispose()
  }


  override fun onBackPressed() {
    super.onBackPressed()
    onBack()
  }

  private fun onBack() {
    if (isRecording) {
      showToast("Recording in progress, please stop recording first")
      return
    }
    stopRecording(false)
    finish()
  }
}
