package com.example.euphony.app.presentation.ui.dashboard

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.euphony.app.presentation.ui.adapter.MusicListAdapter
import com.example.euphony.app.presentation.ui.base.BaseFragment
import com.example.euphony.core.utils.EventObserver
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

    private lateinit var musicAdapter: MusicListAdapter

    override fun initView() {

        setupRecyclerView()
        viewModel.searchMusic("johnson")
    }

    override fun obverseLiveData() {
        with(viewModel) {
            musicItems.observe(viewLifecycleOwner, EventObserver{
                musicAdapter.submitList(it)
            })
        }
    }

    private fun setupRecyclerView() {
        musicAdapter = MusicListAdapter { musicItem ->
            // TODO: Handle item click
        }

        binding.rvSongList.apply {
            adapter = musicAdapter
            layoutManager = LinearLayoutManager(requireContext())

            // Add item decoration for spacing
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}