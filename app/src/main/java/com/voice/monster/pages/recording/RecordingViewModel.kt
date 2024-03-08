package com.voice.monster.pages.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecordingViewModel : ViewModel() {

  private var preStartTimerJob: Job? = null
  private var recordingTimerJob: Job? = null
  private val mutTimerCount = MutableLiveData(3)
  val timerCount: LiveData<Int> = mutTimerCount
  private val mutRecordingTimerCount = MutableLiveData(0)
  val recordingTimerCount: LiveData<Int> = mutRecordingTimerCount
  private var isRecording = false

  fun perStartRecording() {
    preStartTimerJob = viewModelScope.launch {
      while ((mutTimerCount.value ?: 0) > 0) {
        val next = (mutTimerCount.value ?: 0) - 1
        mutTimerCount.postValue(next)
        delay(1000L)
      }
    }
  }

  fun startRecordingTimer() {
    if (isRecording) return
    isRecording = true
    recordingTimerJob = viewModelScope.launch {
      while (isRecording) {
        mutRecordingTimerCount.postValue(mutRecordingTimerCount.value!! + 1)
        delay(1000L)
      }
    }
  }

  fun stopRecordingTimer() {
    isRecording = false
    recordingTimerJob?.cancel()
  }

  fun dispose() {
    preStartTimerJob?.cancel()
    startRecordingTimer()
  }
}

