package com.gunishjain.newsapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.di.component.ActivityComponent
import com.gunishjain.newsapp.di.component.DaggerActivityComponent
import com.gunishjain.newsapp.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, VB: ViewBinding>: AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun buildActivityComponent() = DaggerActivityComponent.builder()
        .applicationComponent((application as NewsApplication).applicationComponent)
        .activityModule(ActivityModule(this)).build()

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun getViewBinding(): VB

    protected abstract fun setupUI()

    protected abstract fun setupObserver()


}