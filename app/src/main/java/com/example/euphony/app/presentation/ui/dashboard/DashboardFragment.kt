package com.example.euphony.app.presentation.ui.dashboard

import androidx.fragment.app.viewModels
import com.example.euphony.app.presentation.ui.base.BaseFragment
import com.example.euphony.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    companion object {
        val TAG get() = "TAG.${DashboardFragment::class.java.simpleName}"

        fun newInstance() = DashboardFragment()
    }

    private val viewModel: DashboardViewModel by viewModels()

    override fun initView() {
        binding.message.text = "SHOW FRAGMENT"
    }
}