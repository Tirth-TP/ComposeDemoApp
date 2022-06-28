package com.ComposeDemoApp.ui.navigation

import com.ComposeDemoApp.R

/**
 * Created by Tirth Patel.
 */
sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Gallery: BottomNavItem("Gallery",R.drawable.ic_gallery,"gallery")
    object Todo: BottomNavItem("Todo",R.drawable.ic_todo,"todo")
}