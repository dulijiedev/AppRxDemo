package com.dol.networklib.functions

import android.util.Log
import com.dol.networklib.BuildConfig
import io.reactivex.functions.Function

/**
 * Created by dlj on 2019/9/23.
 */
class HttpResultTFunction<T> : Function<T, T> {
    override fun apply(t: T): T {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "${t.toString()}")
        }
        //具体如何失败 需要在此处进行判断抛出
        return t
    }

}