package com.dol.apprxdemo.api

import com.dol.networklib.NetUtils

/**
 * Created by dlj on 2019/9/23.
 */
//const val BASE_URL = "https://tcapp.dcpay.vip/merchant_app/"
const val BASE_URL = "http://192.168.1.189:8080/merchant_app/"

object RetrofitHelper {

    val apiService: ApiService
        get() = NetUtils.get().getApiService(BASE_URL, ApiService::class.java)
}