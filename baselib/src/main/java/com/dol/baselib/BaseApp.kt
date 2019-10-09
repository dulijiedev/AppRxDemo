package com.dol.baselib

import android.app.Application
import com.dol.baselib.utils.DelegatesExt
import com.dol.baselib.utils.UIUtils

/**
 * Created by dlj on 2019/9/29.
 */
open class BaseApp : Application() {
    companion object {
        var instance: Application by DelegatesExt.notNullSingleValue()
    }
    override fun onCreate() {
        super.onCreate()
        instance=this
    }
}