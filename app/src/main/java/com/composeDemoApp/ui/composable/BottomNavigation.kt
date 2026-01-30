package com.composeDemoApp.ui.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.composeDemoApp.ui.navigation.BottomNavItem

/**
 * Created by Tirth Patel.
 */

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Gallery,
        BottomNavItem.Todo
    )
    NavigationBar(
        containerColor = colorScheme.primaryContainer,
        contentColor = colorScheme.onPrimaryContainer
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.4f)
                )
            )
        }
    }
}
