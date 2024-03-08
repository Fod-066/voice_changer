package com.voice.monster.pages.change

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.voice.monster.R
import com.voice.monster.ext.showToast
import com.voice.monster.ext.singleClick

class SaveFileDialog(context: Context, onSaveClick: (String, Dialog) -> Unit) : Dialog(context) {

  init {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.save_file_dialog, null)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(view)
    setCancelable(false)
    setCanceledOnTouchOutside(false)
    window?.setLayout(262.dpToPx(), 205.dpToPx())
    val edit = view.findViewById<AppCompatEditText>(R.id.file_name_edit)
    val cancelTv = view.findViewById<AppCompatTextView>(R.id.cancel_tv)
    val saveTv = view.findViewById<AppCompatTextView>(R.id.save_tv)
    val saveHintTv = view.findViewById<AppCompatTextView>(R.id.saving_tv)
    cancelTv.singleClick { dismiss() }
    saveTv.singleClick {
      if (edit.text?.isNullOrEmpty() == true) {
        context.showToast("Please enter file name")
        return@singleClick
      }
      edit.isVisible = false
      saveHintTv.isVisible = true
      onSaveClick.invoke(edit.text?.toString() ?: "", this)
    }
  }

  fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
  }
}