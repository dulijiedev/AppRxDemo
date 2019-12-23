package com.dol.apprxdemo

import com.dol.baselib.utils.Poko

/**
 * Created by dlj on 2019/12/17.
 */

//更新实体
@Poko
data class AppUpdateParams(
    var created: String,
    var downloadAddress: String,
    var id: Int,
    var isUpdate: Int,  //是否强更(0,非强更;1,强更)
    var leastVersion: Int,
    var modified: String,
    var phoneType: Int,
    var saasId: String,
    var updateInformation: String?="更新内容",
    var versionCode: Int,
    var versionName: String
)