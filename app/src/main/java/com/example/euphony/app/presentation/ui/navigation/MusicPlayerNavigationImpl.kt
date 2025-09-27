package com.example.euphony.app.presentation.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.euphony.R
import com.example.euphony.app.presentation.ui.dashboard.DashboardFragment
import com.example.euphony.core.utils.AppNavigation
import javax.inject.Inject

class MusicPlayerNavigationImpl @Inject constructor(
    private val appNavigation: AppNavigation
)  : MusicPlayerNavigation {
    override val containerId: Int
        get() = R.id.container
    override var currentFragment: Fragment? = null

    override fun openDashboard(fm: FragmentManager) {
        currentFragment = DashboardFragment.newInstance()
        appNavigation.pushFragmentClearBackStack(
            fragmentManager = fm,
            containerId = containerId,
            fragment = currentFragment!!,
            fragmentTag = DashboardFragment.TAG
        )
    }


}