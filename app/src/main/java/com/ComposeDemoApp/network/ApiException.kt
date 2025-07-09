package com.ComposeDemoApp.network

import java.io.IOException

/**
 * Created by Tirth Patel.
 */
class ApiException(message: String, var errno: String?, var code: Int) : IOException(message)
