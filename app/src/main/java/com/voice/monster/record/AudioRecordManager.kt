package com.voice.monster.record

object AudioRecordManager {

  const val AMR = 0
  const val PCM = 1

  fun getInstance(mode: Int): IAudioRecord {
    return AudioRecordWav()
  }
}