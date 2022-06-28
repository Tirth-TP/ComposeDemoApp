package com.ComposeDemoApp.data.remote.model.response

data class ErrorModel(
    var message: String = "",
    var errno: String? = null,
    var code: Int = 0
)
