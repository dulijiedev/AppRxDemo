package com.dol.apprxdemo

import android.app.Application
import android.util.Log
import com.dol.baselib.BaseApp
import com.dol.baselib.utils.DelegatesExt
import com.dol.baselib.utils.UIUtils
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by dlj on 2019/9/23.
 */
class MyApp : Application() {

    companion object {
        var instance: MyApp by DelegatesExt.notNullSingleValue<MyApp>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        BaseApp.instance=this
        UIUtils.init(this)
        RxJavaPlugins.setErrorHandler { throwable ->
            Log.e("DLJ", "setErrorHandler=$throwable")
            /**
             * RxJava2的一个重要的设计理念是：不吃掉任何一个异常,即抛出的异常无人处理，便会导致程序崩溃
             * 这就会导致一个问题，当RxJava2“downStream”取消订阅后，“upStream”仍有可能抛出异常，
             * 这时由于已经取消订阅，“downStream”无法处理异常，此时的异常无人处理，便会导致程序崩溃
             */
        }
    }
}