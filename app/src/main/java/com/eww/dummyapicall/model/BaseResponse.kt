package com.eww.dummyapicall.model

import java.io.Serializable


open class BaseResponse(
    var message: String? = null,
    var status: Boolean? = null
)

