package com.dol.apprxdemo.api

import com.dol.apprxdemo.AppUpdateParams
import com.dol.networklib.data.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by dlj on 2019/9/23.
 */

interface ApiService {

    @GET("heartbeat")
    fun heartBeat(): Observable<BaseResponse<Unit>>

    /**获取版本信息*/
    @GET("app/version/list")
    fun getAppVersion(@Query("type") type: Int, @Query("sign") sign: String = "", @Query("accesskey") accesskey: String = ""): Observable<BaseResponse<AppUpdateParams>>
}