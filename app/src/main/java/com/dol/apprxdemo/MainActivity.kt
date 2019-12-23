package com.dol.apprxdemo

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dol.apprxdemo.databinding.ActivityMainBinding
import com.dol.apprxdemo.vm.MainVm
import com.dol.appupdate.config.UpdateConfiguration
import com.dol.appupdate.manager.DownloadManager
import com.dol.baselib.BaseInjectAt
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseInjectAt<ActivityMainBinding, MainVm>() {
    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    val permissions=RxPermissions(this)

    override fun initData() {
        super.initData()
        tv_test.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestAt::class.java))
        }

        permissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe{
                if(it.granted){
                    viewModel?.getVersions()
                }else{
                    Log.d("DLJ","权限拒绝")
                }
            }
//        val view = LayoutInflater.from(context).inflate(R.layout.prepare_head_view, null, false)
//        val bindingHead = DataBindingUtil.bind<PrepareHeadViewBinding>(view)
//        bindingHead?.viewModel = initViewModel()
//        bindingHead?.setLifecycleOwner(this)
//        viewModel?.adapter?.addHeaderView(view)
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel?.updateValue?.observe(this, Observer {
            Log.d("DLJ","${it?.needUpdate()?.byForce()}")
            Log.d("DLJ","${it?.needUpdate()?.byForce()}")
            Log.d("DLJ","${it?.needUpdate()?.ipaUrl}")
            Log.d("DLJ","${it?.needUpdate()?.to}")

            tv_test.text="${it?.needUpdate()?.byForce()}\n${it?.needUpdate()?.ipaUrl}\n${it?.needUpdate()?.to}"

            val configuration = UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()RetrofitHelper
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //支持断点下载
                .setBreakpointDownload(true)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置强制更新
                .setForcedUpgrade(it?.needUpdate()?.byForce()?:false)
//                    //设置对话框按钮的点击监听
//                    .setButtonClickListener(this)
//                    //设置下载过程的监听
//                    .setOnDownloadListener(this)

            val manager = DownloadManager.getInstance(this@MainActivity)
            manager.setApkName("dcpay.apk")
                .setApkUrl(it?.needUpdate()?.ipaUrl?:"")
//                        .setApkUrl("https://raw.githubusercontent.com/azhon/AppUpdate/master/apk/appupdate.apk")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowNewerToast(true)
                .setApkVersionCode(2)
                .setConfiguration(configuration)
                .setDownloadPath("${Environment.getExternalStorageDirectory()}/AppUpdate")
//                .setApkVersionBean(it?.needUpdate()?.to)
                .setAuthorities(packageName)
                .setApkDescription(it?.needUpdate()?.news()?:"")
                .download(it?.needUpdate()!=null)

        })
    }

}
