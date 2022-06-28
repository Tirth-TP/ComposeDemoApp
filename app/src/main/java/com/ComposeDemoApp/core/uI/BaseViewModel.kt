package com.ComposeDemoApp.core.uI

import androidx.lifecycle.ViewModel
import com.ComposeDemoApp.data.remote.model.response.ErrorModel
import com.ComposeDemoApp.util.extensionFunction.mutableLiveData

/**
 * Created by JeeteshSurana.
 */

open class BaseViewModel : ViewModel() {
    var mError = mutableLiveData(ErrorModel())
}