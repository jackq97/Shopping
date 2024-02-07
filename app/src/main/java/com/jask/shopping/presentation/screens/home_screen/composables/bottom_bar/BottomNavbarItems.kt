package com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object TimerSettingScreen : BottomNavigationItem("timer_setting_screen", Icons.Default.Menu, "Timers")
    object SettingsScreen : BottomNavigationItem("settings_screen", Icons.Default.Menu, "Settings")
    object InfoScreen : BottomNavigationItem("info_screen", Icons.Default.Menu, "Info")
}