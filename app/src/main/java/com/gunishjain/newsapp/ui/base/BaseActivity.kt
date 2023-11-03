package com.gunishjain.newsapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding>(
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    lateinit var viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

    protected abstract fun getViewBinding(): VB

    protected abstract fun setupUI()

    protected abstract fun setupObserver()


}