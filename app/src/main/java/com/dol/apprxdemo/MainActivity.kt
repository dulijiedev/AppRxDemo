package com.dol.apprxdemo

import com.dol.apprxdemo.databinding.ActivityMainBinding
import com.dol.apprxdemo.vm.MainVm
import com.dol.baselib.BaseInjectAt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseInjectAt<ActivityMainBinding, MainVm>() {
    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        tv_test.setOnClickListener{
            viewModel?.destroyScope()
        }
    }

}
