package com.dol.baselib

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.dol.rxlifecycle.scopes.ScopeViewModel
import kotlinx.android.synthetic.main.base_title_view.*
import java.lang.reflect.ParameterizedType

/**
 * Created by dlj on 2019/9/23.
 */
abstract class BaseInjectAt<V : ViewDataBinding, VM : ScopeViewModel> : AppCompatActivity(), BaseCallback {
    lateinit var binding: V
    var viewModel: VM? = null
    private var viewModelId = -1
    protected var loadDialog: Dialog? = null
    protected var requestCode = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
        initViewDataBinding()
        initData()
        initViewObservable()
        initToolbar()
    }

    override fun initParams() {
    }

    private fun initViewDataBinding() {
        binding = DataBindingUtil.setContentView(this, getContentLayout())
        with(binding) {
            lifecycleOwner = this@BaseInjectAt
        }
        toolbarFitSystemWindows()
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClazz: Class<out AndroidViewModel>
            val type = javaClass.genericSuperclass
            modelClazz = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<out AndroidViewModel>
            } else {
                ScopeViewModel::class.java
            }
            viewModel = createViewModel(this, modelClazz) as? VM
        }
        binding.setVariable(viewModelId, viewModel)
    }

    private fun <T : ViewModel> createViewModel(activity: AppCompatActivity, cls: Class<T>): T {
        return ViewModelProviders.of(activity).get(cls)
    }

    open fun toolbarFitSystemWindows() {

    }

    open fun initViewModel(): VM? {
        return null
    }

    abstract fun getContentLayout(): Int

    abstract fun initVariableId(): Int


    override fun initData() {
    }

    override fun initViewObservable() {
    }

    private fun initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel = null
        binding.unbind()
    }
}