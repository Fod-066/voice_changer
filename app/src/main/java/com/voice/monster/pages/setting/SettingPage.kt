package com.voice.monster.pages.setting

import com.voice.monster.databinding.SettingPageBinding
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage

class SettingPage : BasePage<SettingPageBinding>() {

  override fun initBinding() = SettingPageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.backIv.singleClick { finish() }
  }
}