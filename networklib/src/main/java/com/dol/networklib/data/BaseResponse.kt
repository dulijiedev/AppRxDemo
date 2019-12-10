package com.dol.networklib.data

/**
 * Created by dlj on 2019/9/23.
 * back response
 */
class BaseResponse<T> {

    var status: Int = 0

    var msg: String = ""

    var data: T? = null

    var success = status == 200

    override fun toString(): String {
        return "BaseResponse(status:'$status',msg:'$msg', data:'$data')"
    }

    fun transform() : Optional<T>{
        return Optional(data)
    }
}