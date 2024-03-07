package com.voice.monster.pages.change

import androidx.recyclerview.widget.GridLayoutManager
import com.voice.monster.databinding.ChangerPageBinding
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage

class ChangePage : BasePage<ChangerPageBinding>() {

  override fun initBinding() = ChangerPageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    binding.effectRv.adapter = EffectAdapter(this) { effect -> }
    binding.effectRv.layoutManager = GridLayoutManager(this, 3)
    binding.backIv.singleClick { finish() }
  }
}