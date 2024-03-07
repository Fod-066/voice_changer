package com.voice.monster.ext

import android.os.SystemClock
import android.view.View

private const val DEFAULT_DEBOUNCE_TIME = 800L

private var lastClickTime: Long = 0

fun View.singleClick(
  debounceTime: Long = DEFAULT_DEBOUNCE_TIME,
  onClick: (View) -> Unit
) {
  setOnClickListener { view ->
    val currentTime = SystemClock.elapsedRealtime()
    if (currentTime - lastClickTime >= debounceTime) {
      lastClickTime = currentTime
      onClick(view)
    }
  }
}