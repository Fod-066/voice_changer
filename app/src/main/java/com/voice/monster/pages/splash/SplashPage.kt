package com.voice.monster.pages.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.animation.addListener
import com.voice.monster.databinding.SplashPageBinding
import com.voice.monster.pages.BasePage

class SplashPage : BasePage<SplashPageBinding>() {

  override fun initBinding() = SplashPageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    val alphaAnimator = ObjectAnimator.ofFloat(binding.logoIv, View.ALPHA, 0f, 1f)
    val alphaAnimator2 = ObjectAnimator.ofFloat(binding.textIv, View.ALPHA, 0f, 1f)
    val animatorSet = AnimatorSet()
    animatorSet.playTogether(alphaAnimator, alphaAnimator2)
    animatorSet.duration = 3000L
    animatorSet.addListener(onEnd = {

    })
    animatorSet.start()
  }
}