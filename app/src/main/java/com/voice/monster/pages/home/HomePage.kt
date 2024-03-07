package com.voice.monster.pages.home

import androidx.core.view.isVisible
import com.voice.monster.databinding.HomePageBinding
import com.voice.monster.pages.BasePage

class HomePage : BasePage<HomePageBinding>() {

  override fun initBinding() = HomePageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.listRv.isVisible = false
    binding.emptyIv.isVisible = true
  }
}