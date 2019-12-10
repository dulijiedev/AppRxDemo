package com.dol.apprxdemo.vm

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dol.baselib.utils.UIUtils
import com.dol.rxlifecycle.scopes.ScopeViewModel

/**
 * Created by dlj on 2019/10/9.
 */
class TestVM(val value: Int, application: Application) : ScopeViewModel(application) {

    class TestVmFactory(private val value: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TestVM(value, UIUtils.getContext() as Application) as T
        }
    }

    val test = MediatorLiveData<String>()

    init {
        test.value = "$value"
    }
}