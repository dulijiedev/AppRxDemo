package com.dol.apprxdemo.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.dol.rxlifecycle.life
import com.dol.rxlifecycle.lifeOnMain
import com.dol.rxlifecycle.scopes.ScopeViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by dlj on 2019/9/29.
 */
class MainVm(application: Application) : ScopeViewModel(application) {
    val test = MediatorLiveData<String>()

    init {
        Observable.interval(1, 1, TimeUnit.SECONDS)
            .lifeOnMain(this)
            .subscribe(Consumer {
                test.value = "interval->$it"
                Log.e("DLJ", "MainVm:-long->$it-->${Thread.currentThread().name}")
            }/*, Consumer {
                Log.e("DLJ", "MainVm:-onError->$it")
            }*/)
    }



}