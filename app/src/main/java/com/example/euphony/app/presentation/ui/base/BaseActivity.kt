package com.example.euphony.app.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<vb: ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> vb
) : AppCompatActivity() {

    private var _binding: vb? = null
    protected val binding get() = _binding!!

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        initView()
    }
}