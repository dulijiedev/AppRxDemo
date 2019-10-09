package com.dol.baselib.command

/**
 * Created by dlj on 2018/12/24.
 */
interface BindingConsumer<T> {
    fun call(t: T)
}
