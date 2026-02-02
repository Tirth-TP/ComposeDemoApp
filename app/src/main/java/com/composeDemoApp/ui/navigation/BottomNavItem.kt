package com.composeDemoApp.ui.navigation

import com.composeDemoApp.R

/**
 * Created by Tirth Patel.
 */
sealed class BottomNavItem(var titleResId: Int, var icon: Int, var screenRoute: String) {

    object Home : BottomNavItem(R.string.nav_home, R.drawable.ic_home, "home")
    object Gallery : BottomNavItem(R.string.nav_gallery, R.drawable.ic_gallery, "gallery")
    object Todo : BottomNavItem(R.string.nav_todo, R.drawable.ic_todo, "todo")
}