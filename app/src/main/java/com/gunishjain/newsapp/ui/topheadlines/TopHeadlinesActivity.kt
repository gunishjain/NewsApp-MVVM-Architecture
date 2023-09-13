package com.gunishjain.newsapp.ui.topheadlines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.ActivityTopHeadlinesBinding
import com.gunishjain.newsapp.di.component.DaggerActivityComponent
import com.gunishjain.newsapp.di.module.ActivityModule
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlinesActivity : AppCompatActivity() {


    @Inject
    lateinit var topHeadlinesVM : TopHeadlinesViewModel

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlinesAdapter

    private lateinit var binding : ActivityTopHeadlinesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=topHeadlineAdapter
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlinesVM.uiState.collect {
                    when(it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE

                        }
                        is UiState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlinesActivity,it.message,Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        topHeadlineAdapter.addArticles(articleList)
        topHeadlineAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
            DaggerActivityComponent.builder()
                .applicationComponent((application as NewsApplication).applicationComponent)
                .activityModule(ActivityModule(this)).build().inject(this)
    }
}