package com.voice.monster.pages.home

import android.content.Intent
import androidx.core.view.isVisible
import com.voice.monster.databinding.HomePageBinding
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import com.voice.monster.pages.recording.RecordingPage
import com.voice.monster.pages.setting.SettingPage

class HomePage : BasePage<HomePageBinding>() {

  override fun initBinding() = HomePageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.listRv.isVisible = false
    binding.emptyIv.isVisible = true
    binding.settingIv.singleClick { startActivity(Intent(this, SettingPage::class.java)) }
    binding.recordCl.singleClick { startActivity(Intent(this, RecordingPage::class.java)) }
  }
}