package com.voice.monster.pages.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.voice.monster.R
import com.voice.monster.ext.showToast
import com.voice.monster.ext.singleClick

class PermissionDialog(context: Context) : Dialog(context) {

  init {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.permisson_dialog, null)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(view)
    setCancelable(false)
    setCanceledOnTouchOutside(false)
    window?.setLayout(262.dpToPx(), 205.dpToPx())
    val cancelTv = view.findViewById<AppCompatTextView>(R.id.cancel_tv)
    val saveTv = view.findViewById<AppCompatTextView>(R.id.save_tv)
    val saveHintTv = view.findViewById<AppCompatTextView>(R.id.saving_tv)
    cancelTv.singleClick { dismiss() }
    saveTv.singleClick {
      val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      intent.data = Uri.fromParts("package", context.packageName, null)
      context.startActivity(intent)
      dismiss()
    }
  }

  fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
  }
}