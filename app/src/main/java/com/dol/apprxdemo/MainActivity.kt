package com.dol.apprxdemo

import android.content.Intent
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
        tv_test.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestAt::class.java))
        }

//        val view = LayoutInflater.from(context).inflate(R.layout.prepare_head_view, null, false)
//        val bindingHead = DataBindingUtil.bind<PrepareHeadViewBinding>(view)
//        bindingHead?.viewModel = initViewModel()
//        bindingHead?.setLifecycleOwner(this)
//        viewModel?.adapter?.addHeaderView(view)
    }

}
