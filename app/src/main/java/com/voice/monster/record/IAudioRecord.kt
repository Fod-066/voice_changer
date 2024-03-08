package com.voice.monster.record

interface IAudioRecord {

  fun setRecordListener(listener: RecordListener?)

  fun startRecording(outFile: String)

  fun stopRecording()

  fun getOutFilePath(): String


  interface RecordListener {
    fun start()

    fun volume(volume: Int)

    fun stop(outPath: String)
  }

}