package com.dol.appupdate.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dol.appupdate.R
import com.dol.appupdate.service.DownloadService
import com.dol.appupdate.utils.Constant
import com.dol.appupdate.utils.LogUtil
import com.dol.appupdate.utils.PermissionUtil

/**
 * Created by dlj on 2019/12/19.
 */
class PermissionActivity :AppCompatActivity(){
    private val TAG = Constant.TAG + "PermissionActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionUtil.requestPermission(this, Constant.PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.PERMISSION_REQUEST_CODE) {
            if (grantResults.size == 0) {
                // do something...
                LogUtil.e(TAG, "权限请求回调：grantResults.length = 0")
            } else {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授予了权限
                    download()
                } else {
                    //拒绝了
                    Toast.makeText(
                        this@PermissionActivity,
                        R.string.permission_hint,
                        Toast.LENGTH_LONG
                    ).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            //用户勾选了不在询问
                            goToAppDetailPage(this@PermissionActivity)
                        }
                    }
                }
            }
            finish()
        }
    }

    /**
     * 开始下载
     */
    private fun download() {
        startService(Intent(this, DownloadService::class.java))
    }

    /**
     * 跳转至详情界面
     */
    private fun goToAppDetailPage(context: Context) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", context.packageName, null)
        context.startActivity(intent)
    }
}