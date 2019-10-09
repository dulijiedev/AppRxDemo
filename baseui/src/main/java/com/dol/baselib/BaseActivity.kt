package com.dol.baselib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by dlj on 2019/9/25.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    private lateinit var binding: B
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            lifecycleOwner = this@BaseActivity
        }
        toolbarFitSystemWindows()
    }

    open fun toolbarFitSystemWindows() {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}