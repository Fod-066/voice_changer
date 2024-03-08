package com.voice.monster.record

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.demon.soundcoding.PcmToWav
import com.voice.monster.App
import com.voice.monster.ext.getRecordFilePath
import com.voice.monster.ext.launchIO
import kotlinx.coroutines.GlobalScope
import java.io.FileOutputStream

@SuppressLint("MissingPermission")
class AudioRecordWav : IAudioRecord {
  private val TAG = "AudioRecordPcm"

  private var audioRecord: AudioRecord? = null
  private var listener: IAudioRecord.RecordListener? = null

  private val AUDIO_INPUT = MediaRecorder.AudioSource.MIC

  private val AUDIO_SAMPLE_RATE = 8000

  private val AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO

  private val AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT

  protected var wavFileName: String = ""
  protected var pcmFileName: String = ""

  private var bufferSizeInBytes = 0

  init {
    bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING)
    if (audioRecord == null) {
      audioRecord = AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes)
    }
  }

  override fun setRecordListener(listener: IAudioRecord.RecordListener?) {
    this.listener = listener
  }

  override fun startRecording(outFile: String) {
    wavFileName = outFile
    pcmFileName = App.mContext.getRecordFilePath(2)
    Log.i(TAG, "startRecording: ${audioRecord?.state}")
    if (audioRecord?.state == AudioRecord.STATE_INITIALIZED) {
      listener?.start()
      audioRecord?.startRecording()
      GlobalScope.launchIO {
        writeDataTOFile()
      }
    } else {
      Log.i(TAG, "startRecording: state error")
    }
  }

  override fun stopRecording() {
    GlobalScope.launchIO {
      wavFileName = PcmToWav.makePcmToWav(pcmFileName, wavFileName,true)
      listener?.stop(wavFileName)
    }
    audioRecord?.stop()
    audioRecord?.release()
    audioRecord = null
  }

  override fun getOutFilePath(): String = wavFileName


  private fun writeDataTOFile() {
    runCatching {
      val audiodata = ByteArray(bufferSizeInBytes)
      val fos = FileOutputStream(pcmFileName, false)
      audioRecord?.run {
        Log.i(TAG, "writeDataTOFile: $state")
        while (state == AudioRecord.STATE_INITIALIZED) {
          val readsize = read(audiodata, 0, bufferSizeInBytes)
          if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
            var sum = 0
            for (i in 0 until readsize) {
              sum += kotlin.math.abs(audiodata[i].toInt())
            }
            if (readsize > 0) {
              val raw = sum / readsize
              val lastVolumn = if (raw > 32) raw - 32 else 0
              listener?.volume(lastVolumn)
            }
            if (readsize > 0 && readsize <= audiodata.size) {
              fos.write(audiodata, 0, readsize)
            }
          }
        }
        fos.close()
      }
    }.onFailure {
      Log.e(TAG, "writeDataTOFile: ", it)
    }
  }

}