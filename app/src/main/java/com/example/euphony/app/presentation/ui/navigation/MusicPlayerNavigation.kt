package com.example.euphony.app.presentation.ui.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface MusicPlayerNavigation {

    val containerId: Int @IdRes get

    var currentFragment: Fragment?

    fun openDashboard(
        fm: FragmentManager
    )

}