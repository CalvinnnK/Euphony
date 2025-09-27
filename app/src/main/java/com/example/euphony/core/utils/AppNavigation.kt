package com.example.euphony.core.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.euphony.core.extension.isNotEmptyOrBlank
import javax.inject.Inject

interface AppNavigation {
    fun pushReplaceReuseExistingFragment(
        fragmentManager: FragmentManager,
        @IdRes containerId: Int,
        fragment: Fragment,
        fragmentTag: String? = null
    )

    fun pushFragmentClearBackStack(
        fragmentManager: FragmentManager,
        @IdRes containerId: Int,
        fragment: Fragment,
        fragmentTag: String? = null
    )

    fun pushAddFragment(
        fragmentManager: FragmentManager,
        @IdRes containerId: Int,
        fragment: Fragment,
        fragmentTag: String? = null
    )


    fun pushReplaceFragment(
        fragmentManager: FragmentManager,
        @IdRes containerId: Int,
        fragment: Fragment,
        fragmentTag: String? = null
    )

}

class AppNavigationImpl @Inject constructor(

) : AppNavigation {

    override fun pushReplaceReuseExistingFragment(
        fragmentManager: FragmentManager,
        containerId: Int,
        fragment: Fragment,
        fragmentTag: String?
    ) = with(fragmentManager) {
        val existingFragment: Fragment? = fragmentTag?.let(::findFragmentByTag)
        commit {
            if (existingFragment != null) {
                remove(existingFragment)
            }
        }
        pushReplaceFragment(fragmentManager, containerId, fragment, fragmentTag)
    }

    override fun pushFragmentClearBackStack(
        fragmentManager: FragmentManager,
        containerId: Int,
        fragment: Fragment,
        fragmentTag: String?
    ) = with(fragmentManager) {
        for (f in 0 until this.backStackEntryCount) {
            this.popBackStack()
        }
        commit {
            replace(containerId, fragment, fragmentTag)
        }
    }

    override fun pushAddFragment(
        fragmentManager: FragmentManager,
        containerId: Int,
        fragment: Fragment,
        fragmentTag: String?
    ) = with(fragmentManager) {
        commit {
            setReorderingAllowed(true)
            fragmentTag.takeIf { it.isNotEmptyOrBlank() }?.let { tag ->
                add(containerId, fragment, tag)
                addToBackStack(tag)
            } ?: add(containerId, fragment)
        }
    }

    override fun pushReplaceFragment(
        fragmentManager: FragmentManager,
        containerId: Int,
        fragment: Fragment,
        fragmentTag: String?
    ) = with(fragmentManager) {
        commit {
            setReorderingAllowed(true)
            fragmentTag.takeIf { it.isNotEmptyOrBlank() }?.let { tag ->
                replace(containerId, fragment, tag)
                addToBackStack(tag)
            } ?: replace(containerId, fragment)
        }
    }
}
