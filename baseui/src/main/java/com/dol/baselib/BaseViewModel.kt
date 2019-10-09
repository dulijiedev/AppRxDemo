package com.dol.baselib

import android.app.Application
import com.dol.baselib.utils.UIUtils
import com.dol.rxlifecycle.scopes.ScopeViewModel

/**
 * Created by dlj on 2019/9/29.
 */
open class BaseViewModel : ScopeViewModel(UIUtils.getContext() as Application)