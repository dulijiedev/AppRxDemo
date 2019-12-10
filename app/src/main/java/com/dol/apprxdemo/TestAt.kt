package com.dol.apprxdemo

import androidx.lifecycle.ViewModelProviders
import com.dol.apprxdemo.databinding.TestAtBinding
import com.dol.apprxdemo.vm.TestVM
import com.dol.baselib.BaseInjectAt

/**
 * Created by dlj on 2019/10/9.
 */
class TestAt :BaseInjectAt<TestAtBinding,TestVM>(){
    override fun getContentLayout(): Int {
        return R.layout.test_at
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): TestVM? {
        return ViewModelProviders.of(this, TestVM.TestVmFactory(666)).get(TestVM::class.java)
    }

    override fun initData() {
        super.initData()
    }


}