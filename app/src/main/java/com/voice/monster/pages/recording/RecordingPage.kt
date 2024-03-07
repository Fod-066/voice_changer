package com.voice.monster.pages.recording

import com.voice.monster.databinding.RecordingPageBinding
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage

class RecordingPage : BasePage<RecordingPageBinding>() {

  override fun initBinding() = RecordingPageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.backIv.singleClick { finish() }
  }

  override fun onBackPressed() {
    super.onBackPressed()
  }
}
