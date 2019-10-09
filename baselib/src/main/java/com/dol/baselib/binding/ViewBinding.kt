package com.dol.baselib.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.dol.baselib.command.BindingCommand
import java.util.concurrent.TimeUnit
import com.jakewharton.rxbinding3.view.clicks

/**
 * Created by dlj on 2019/9/29.
 */
/**
 * requireAll 是意思是是否需要绑定全部参数, false为否
 * View的onClick事件绑定
 * onClickCommand 绑定的命令,
 * isThrottleFirst 是否开是否开启防止过快点击启防止过快点击
 */
@BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
fun onClickCommand(view: View, clickCommand: BindingCommand<*>?, isThrottleFirst: Boolean = false) {
    if (isThrottleFirst) {
        view.clicks()
            .subscribe {
                if (clickCommand != null) {
                    clickCommand?.execute()
                }
            }
    } else {
        view.clicks()
            .throttleFirst(1L, TimeUnit.SECONDS)//1秒钟内只允许点击1次
            .subscribe {
                if (clickCommand != null) {
                    clickCommand?.execute()
                }
            }
    }
}