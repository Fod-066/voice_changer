package com.voice.monster.pages.file

import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.voice.monster.databinding.FilePageBinding
import com.voice.monster.ext.getModFilePath
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import com.voice.monster.pages.change.ChangePage
import com.voice.monster.pages.recording.RecordingPage
import java.io.File

class FilePage : BasePage<FilePageBinding>() {

  var isMyFile: Boolean = true

  override fun initBinding() = FilePageBinding.inflate(layoutInflater)

  override fun onBindingFinish() {
    isMyFile = intent.getBooleanExtra("IsMyFile", true)
    binding.titleTv.text = if (isMyFile) "MyFile" else "Open file"
    binding.emptyTv.singleClick { startActivity(Intent(this, RecordingPage::class.java)) }
    binding.backIv.singleClick { finish() }
    initFiles()
  }

  private fun initFiles() {
    val directoryPath =
      this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + if (isMyFile) "/Mod/" else "/Record/"
    val directory = File(directoryPath)
    val fileList = mutableListOf<File>()

    if (directory.exists() && directory.isDirectory) {
      val files = directory.listFiles()
      if (files != null) {
        for (file in files) {
          if (file.isFile) {
            fileList.add(file)
          }
        }
      }
    }
    Log.d("File", "file size : ${fileList.size}")
    val isEmpty = fileList.isEmpty()
    if (isEmpty) {
      binding.emptyTv.isVisible = true
      binding.emptyIv.isVisible = true
      binding.fileRv.isVisible = false
    } else {
      binding.emptyTv.isVisible = false
      binding.emptyIv.isVisible = false
      binding.fileRv.isVisible = true
      binding.fileRv.adapter = FileAdapter(this, fileList) { file, seconds ->
        startActivity(Intent(this, ChangePage::class.java).apply {
          putExtra("FilePath", file.path)
          putExtra("Seconds", seconds)
        })
      }
      binding.fileRv.layoutManager = LinearLayoutManager(this)
    }
  }
}