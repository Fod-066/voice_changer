package com.voice.monster.pages.file

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voice.monster.databinding.FileItemViewBinding
import com.voice.monster.ext.formatMS
import com.voice.monster.ext.singleClick
import java.io.File
import java.io.IOException

class FileAdapter(
  val context: Context,
  val files: List<File>,
  val onFileClick: (File, Int) -> Unit
) :
  RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

  inner class FileViewHolder(val binding: FileItemViewBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
    val binding = FileItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
    return FileViewHolder(binding)
  }

  override fun getItemCount(): Int = files.size

  override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
    val file = files[position]
    holder.binding.apply {
      fileNameTv.text = file.name
      val seconds = getAudioFileDuration(file.absolutePath)
      fileTimeTv.text = seconds.formatMS()
      root.singleClick { onFileClick.invoke(file, seconds) }
    }
  }

  fun getAudioFileDuration(filePath: String): Int {
    val mediaPlayer = MediaPlayer()
    try {
      mediaPlayer.setDataSource(filePath)
      mediaPlayer.prepare()
      return mediaPlayer.duration / 1000
    } catch (e: IOException) {
      e.printStackTrace()
    } finally {
      mediaPlayer.release()
    }
    return 0
  }
}