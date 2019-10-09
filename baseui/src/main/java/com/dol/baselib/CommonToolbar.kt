package com.dol.baselib

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dol.baselib.command.BindingCommand
import com.dol.baselib.utils.UIUtils

/**
 * Created by dlj on 2019/5/17.
 */
open class CommonToolbar {

    var title: MutableLiveData<String>? = null
    var rightText: MutableLiveData<String>? = null
    var rightAction: BindingCommand<Unit>? = null
    //drawableRight
    var rightImg: MediatorLiveData<Int>? = null

    var style: ToolBarEm? = null

    //返回键
    var iconBack = MediatorLiveData<Int>()
    //title颜色
    var textColor = MediatorLiveData<Int>()

    constructor(
        title: MutableLiveData<String>
    ) : this(title, null, null)

    constructor(
        title: MutableLiveData<String>,
        rightText: MutableLiveData<String>?,
        rightAction: BindingCommand<Unit>?
    ) : this(title, rightText, rightAction, null)

    constructor(
        title: MutableLiveData<String>,
        rightText: MutableLiveData<String>?,
        rightAction: BindingCommand<Unit>?,
        rightImg: MediatorLiveData<Int>?
    ) : this(title, rightText, rightAction, rightImg, ToolBarEm.Normal)

    constructor(
        title: MutableLiveData<String>,
        rightText: MutableLiveData<String>?,
        rightAction: BindingCommand<Unit>?,
        rightImg: MediatorLiveData<Int>?,
        style: ToolBarEm?
    ) {
        this.title = title
        this.rightText = rightText
        this.rightAction = rightAction
        this.rightImg = rightImg
        this.style = style
        initTitleStyle()
    }


    private fun initTitleStyle() {
        when (style) {
            ToolBarEm.Normal -> {
                iconBack.value = R.drawable.ic_left_back
                textColor.value = UIUtils.getColor(R.color.title_text_color)
            }
            ToolBarEm.White -> {
                iconBack.value = R.drawable.ic_back_white
                textColor.value = UIUtils.getColor(R.color.title_text_white)
            }
        }
    }
}

enum class ToolBarEm {
    //默认类型黑色字体黑色返回icon
    Normal,
    //白色字体白色返回icon
    White
}