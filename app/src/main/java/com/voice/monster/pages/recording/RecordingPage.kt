package com.voice.monster.pages.recording

import android.content.Intent
import androidx.core.view.isVisible
import com.voice.monster.databinding.RecordingPageBinding
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import com.voice.monster.pages.change.ChangePage

class RecordingPage : BasePage<RecordingPageBinding>() {

  override fun initBinding() = RecordingPageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.backIv.singleClick { finish() }
    binding.stopIv.isVisible = true
    binding.stopIv.singleClick { startActivity(Intent(this, ChangePage::class.java)) }
  }

  override fun onBackPressed() {
    super.onBackPressed()
  }
}
