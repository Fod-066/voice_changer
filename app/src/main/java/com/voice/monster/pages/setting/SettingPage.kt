package com.voice.monster.pages.setting

import android.content.Intent
import android.net.Uri
import com.voice.monster.databinding.SettingPageBinding
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage

class SettingPage : BasePage<SettingPageBinding>() {

  override fun initBinding() = SettingPageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.backIv.singleClick { finish() }
    binding.ppCl.singleClick {
      openBrowser()
    }
  }
  fun openBrowser(url: String = "https://google.com") {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
  }
}