package com.voice.monster.pages.home

import android.Manifest
import android.content.Intent
import androidx.core.view.isVisible
import com.permissionx.guolindev.PermissionX
import com.voice.monster.databinding.HomePageBinding
import com.voice.monster.ext.showToast
import com.voice.monster.ext.singleClick
import com.voice.monster.pages.BasePage
import com.voice.monster.pages.file.FilePage
import com.voice.monster.pages.recording.RecordingPage
import com.voice.monster.pages.setting.SettingPage

class HomePage : BasePage<HomePageBinding>() {

  override fun initBinding() = HomePageBinding.inflate(layoutInflater)

  private var allGranted: Boolean = false

  override fun onBindingFinish() {
    initPermission()
    binding.listRv.isVisible = false
    binding.emptyIv.isVisible = true
    binding.settingIv.singleClick { startActivity(Intent(this, SettingPage::class.java)) }
    binding.recordCl.singleClick {
      if (!allGranted) {
        initPermission()
        return@singleClick
      }
      startActivity(Intent(this, RecordingPage::class.java))
    }
    binding.myFileCl.singleClick {
      startActivity(Intent(this, FilePage::class.java))
    }
    binding.openFileCl.singleClick {
      startActivity(Intent(this, FilePage::class.java).apply {
        putExtra("IsMyFile", false)
      })
    }
  }

  private fun initPermission() {
    PermissionX.init(this)
      .permissions(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
      )
      .request { allGranted, grantedList, deniedList ->
        this.allGranted = allGranted
        if (allGranted) {
          showToast("Permissions all Granted!")
        } else {
          showToast("These permissions are denied: $deniedList")
        }
      }
  }
}