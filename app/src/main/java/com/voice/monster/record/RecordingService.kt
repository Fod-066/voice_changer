package com.voice.monster.record

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RecordingService : Service() {

  private var audioRecord: IAudioRecord? = null

  override fun onBind(intent: Intent): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
    val mFilePath = intent.getStringExtra("FilePath") ?: ""
    val type = intent.getIntExtra("Type", 0)
    if (audioRecord == null) {
      audioRecord = AudioRecordManager.getInstance(type)
      audioRecord?.setRecordListener(object : IAudioRecord.RecordListener {
        override fun start() {
          Log.i(TAG, "start: ")
        }

        override fun volume(volume: Int) {
          Log.i(TAG, "volume: $volume")
        }

        override fun stop(outPath: String) {
          Log.i(TAG, "stop: $outPath")
        }
      })
    }
    audioRecord?.startRecording(mFilePath)
    return START_STICKY
  }

  override fun onDestroy() {
    super.onDestroy()
    audioRecord?.stopRecording()
  }


  companion object {
    private const val TAG = "RecordingService"
  }
}