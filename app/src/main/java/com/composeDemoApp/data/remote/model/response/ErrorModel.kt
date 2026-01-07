package com.composeDemoApp.data.remote.model.response

data class ErrorModel(
    var message: String = "",
    var errno: String? = null,
    var code: Int = 0
)
