package com.example.euphony.app.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<vb: ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> vb
) : Fragment(){

    private var _binding: vb? = null
    protected val binding get() = _binding!!

    protected abstract fun initView()
    protected abstract fun obverseLiveData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        obverseLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}