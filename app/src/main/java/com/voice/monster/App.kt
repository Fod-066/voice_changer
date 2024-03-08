package com.voice.monster

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    mContext = this.applicationContext
  }


  companion object {
    @SuppressLint("StaticFieldLeak")
    lateinit var mContext: Context
  }
}