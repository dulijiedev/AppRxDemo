package com.dol.apprxdemo.api

import com.dol.apprxdemo.AppUpdateParams
import com.dol.networklib.data.Optional
import com.dol.networklib.observable.getObservable
import io.reactivex.Observable

/**
 * Created by dlj on 2019/12/17.
 */
fun getAppVersions(): Observable<Optional<AppUpdateParams>> {
    return getObservable(RetrofitHelper.apiService.getAppVersion(5))
}