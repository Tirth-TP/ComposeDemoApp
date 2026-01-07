package com.composeDemoApp.ui.navigation

import com.composeDemoApp.R

/**
 * Created by Tirth Patel.
 */
sealed class BottomNavItem(var title:String, var icon:Int, var screenRoute:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Gallery: BottomNavItem("Gallery",R.drawable.ic_gallery,"gallery")
    object Todo: BottomNavItem("Todo",R.drawable.ic_todo,"todo")
}