package com.dol.networklib.functions

import android.util.Log
import com.dol.networklib.BuildConfig
import com.dol.networklib.data.BaseResponse
import com.dol.networklib.exceptions.GeneralException
import io.reactivex.functions.Function

/**
 * Created by dlj on 2019/9/23.
 */
class HttpResultFunction<T> : Function<BaseResponse<T>, BaseResponse<T>> {

    override fun apply(t: BaseResponse<T>): BaseResponse<T> {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "${t.toString()}")
        }
        if (!t.success) {
            throw GeneralException(t.code, t.msg)
        }
        return t
    }

}