package com.dol.appupdate.listener;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface OnButtonClickListener {

    /**
     * 升级按钮点击事件
     */
    int UPDATE = 0;
    /**
     * 取消按钮点击事件
     */
    int CANCEL = 1;

    @IntDef({UPDATE, CANCEL})
    @Retention(RetentionPolicy.SOURCE)
    @interface ID {

    }

    /**
     * 按钮点击回调
     *
     * @param id {@link OnButtonClickListener#UPDATE}
     *           {@link OnButtonClickListener#CANCEL}
     */
    void onButtonClick(@ID int id);
}