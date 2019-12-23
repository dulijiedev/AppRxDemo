package com.dol.apprxdemo.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.dol.apprxdemo.api.getAppVersions
import com.dol.appupdate.utils.ApkUtil
import com.dol.baselib.utils.SingleLiveEvent
import com.dol.baselib.utils.UIUtils
import com.dol.rxlifecycle.lifeOnMain
import com.dol.rxlifecycle.scopes.ScopeViewModel
import com.update.xversion.VersionBean
import com.update.xversion.VersionControl
import io.reactivex.functions.Consumer

/**
 * Created by dlj on 2019/9/29.
 */
class MainVm(application: Application) : ScopeViewModel(application) {
    val test = MediatorLiveData<String>()

    val updateValue = SingleLiveEvent<VersionControl>()

    init {
//        Observable.interval(1, 1, TimeUnit.SECONDS)
//            .lifeOnMain(this)
//            .subscribe(Consumer {
//                test.value = "interval->$it"
//                Log.e("DLJ", "MainVm:-long->$it-->${Thread.currentThread().name}")
//            }/*, Consumer {
//                Log.e("DLJ", "MainVm:-onError->$it")
//            }*/)

//        Observable.create<String> {
//            it.onNext("Good")
//            it.onNext("Bad")
//            it.onNext("Fuck")
//        }.lifeOnMain(this)
//            .subscribe(Consumer {
//                Log.e("DLJ", "MainVm:-Consumer->$it")
//            })


    }

    fun getVersions(){
        getAppVersions()
            .lifeOnMain(this)
            .subscribe(Consumer{
                val version =VersionControl(it.get().isUpdate==1,
                    VersionBean(ApkUtil.getVersionName(UIUtils.getContext())),
                    VersionBean(it.get().versionName),
                    VersionBean(ApkUtil.getVersionName(UIUtils.getContext())),
                    /* it.get().updateInformation*/"更新内容",
                    it.get().downloadAddress
                )
                updateValue.value=version
            }, Consumer {
                Log.d("DLJ","error->${it.message}")
            })
    }


}