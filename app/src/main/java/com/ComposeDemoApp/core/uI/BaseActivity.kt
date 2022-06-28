package com.ComposeDemoApp.core.uI

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.ComposeDemoApp.util.extensionFunction.hideKeyboard
import com.ComposeDemoApp.util.extensionFunction.setTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by JeeteshSurana.
 */

@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

/*    @Inject
    lateinit var preferenceProvider: PreferenceProvider
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setTheme()
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window?.setTheme()
        }
    }
}