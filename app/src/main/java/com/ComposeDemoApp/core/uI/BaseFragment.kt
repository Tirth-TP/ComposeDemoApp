package com.ComposeDemoApp.core.uI

import androidx.fragment.app.Fragment
import com.ComposeDemoApp.util.extensionFunction.hideKeyboard

/**
 * Created by JeeteshSurana.
 */

abstract class BaseFragment : Fragment() {
    override fun onPause() {
        activity?.hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        activity?.hideKeyboard()
        super.onDestroy()
    }
}