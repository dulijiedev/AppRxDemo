package com.dol.networklib.observable

import android.util.Log
import com.dol.networklib.BuildConfig
import com.dol.networklib.data.BaseResponse
import com.dol.networklib.exceptions.ExceptionEngine
import com.dol.networklib.exceptions.GeneralException
import com.dol.networklib.functions.HttpErrorFunction
import com.dol.networklib.functions.HttpResultFunction
import com.dol.networklib.functions.HttpResultTFunction
import com.dol.networklib.functions.TAG
import com.dol.networklib.schedules.obsIO2Main
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by dlj on 2019/9/23.
 */

//带重试的Observable
fun <T> getObservable(observable: Observable<out BaseResponse<T>>): Observable<BaseResponse<T>> {
    val maxRetry = 3
    var retryCount = 0
    return observable
        .map(HttpResultFunction())
        .onErrorResumeNext(HttpErrorFunction())
        .retryWhen {
            it.flatMap { t ->
                val exception = t as? GeneralException
                if (exception != null) {
                    if (t.code == ExceptionEngine.SERVER_ERROR || t.code == ExceptionEngine.SERVER_DATA_ERROR) {
                        Observable.error(t)
                    } else {
                        if (++retryCount <= maxRetry) {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, "getObservable failed, start retry count:$retryCount")
                            }
                            Observable.timer(1, TimeUnit.SECONDS)
                        } else {
                            Observable.error(t)
                        }
                    }
                } else {
                    Observable.error(t)
                }
            }
        }
        .compose(obsIO2Main())
}

//不带重试Observable
fun <T> getSingleObs(observable: Observable<out BaseResponse<T>>): Observable<BaseResponse<T>> {
    return observable
        .map(HttpResultFunction())
        .onErrorResumeNext(HttpErrorFunction())
        .compose(obsIO2Main())
}

//返回结果不是统一类型的
fun <T> getTObservable(observable: Observable<T>): Observable<T> {
    return observable
        .map(HttpResultTFunction())
        .onErrorResumeNext(HttpErrorFunction())
        .compose(obsIO2Main())
}