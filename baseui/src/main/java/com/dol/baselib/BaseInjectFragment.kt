package com.dol.baselib

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.dol.rxlifecycle.scopes.ScopeViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by dlj on 2019/9/26.
 */
abstract class BaseInjectFragment<V : ViewDataBinding, VM : ScopeViewModel> : Fragment(), BaseCallback {

    lateinit var binding: V
    var viewModel: VM? = null
    private var viewModelId = -1
    private var loadDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getContentLayout(inflater, container), container, false)
        with(binding) {
            lifecycleOwner = this@BaseInjectFragment
        }
        initParams()
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
            viewModel = createViewModel(this@BaseInjectFragment, modelClazz) as? VM
        }

        binding.setVariable(viewModelId, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViewObservable()
    }

    abstract fun getContentLayout(inflater: LayoutInflater, container: ViewGroup?): Int

    abstract fun initVariableId(): Int

    abstract fun initViewModel(): VM

    private fun <T : ViewModel> createViewModel(fragment: Fragment, cls: Class<T>): T {
        return ViewModelProviders.of(fragment).get(cls)
    }

    override fun initParams() {
    }

    override fun initData() {
    }

    override fun initViewObservable() {
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel = null
        binding.unbind()

    }
}