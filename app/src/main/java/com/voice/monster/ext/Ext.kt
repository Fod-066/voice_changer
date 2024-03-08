package com.voice.monster.ext

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

fun CoroutineScope.launchIO(block: suspend () -> Unit): Job {
  return this.launch(Dispatchers.IO) { block() }
}

fun CoroutineScope.launchUI(block: suspend () -> Unit): Job {
  return this.launch(Dispatchers.Main) { block() }
}

fun Context.showToast(text: String) {
  Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.getRecordFilePath(type: Int = 0) = this.requireContext().getRecordFilePath(type)

fun Context.getRecordFilePath(type: Int = 0): String {
  val suffix = when (type) {
    1 -> ".wav"
    2 -> ".pcm"
    else -> ".amr"
  }
  val file =
    File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/Record")
  if (!file.exists()) {
    file.mkdirs()
  }
  val path = File(file, "${System.currentTimeMillis()}$suffix")
  if (!path.exists()) {
    file.createNewFile()
  }
  return path.absolutePath
}

fun Context.getModFilePath(fileName: String): String {
  val suffix = ".wav"

  val file =
    File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/Mod")
  if (!file.exists()) {
    file.mkdirs()
  }
  val path = File(file, "$fileName$suffix")
  if (!path.exists()) {
    file.createNewFile()
  }
  return path.absolutePath
}

fun Int.formatHMS(): String {
  val hours = this / 3600
  val minutes = (this % 3600) / 60
  val remainingSeconds = this % 60

  return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

fun Int.formatMS(): String {
  val minutes = (this % 3600) / 60
  val remainingSeconds = this % 60
  return String.format("%02d:%02d", minutes, remainingSeconds)
}