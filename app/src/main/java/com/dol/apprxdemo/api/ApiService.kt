package com.dol.apprxdemo.api

import com.dol.networklib.data.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by dlj on 2019/9/23.
 */

interface ApiService {

    @GET("heartbeat")
    fun heartBeat(): Observable<BaseResponse<Unit>>
}