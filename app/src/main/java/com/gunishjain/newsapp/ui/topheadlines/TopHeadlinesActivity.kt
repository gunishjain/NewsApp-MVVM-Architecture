package com.gunishjain.newsapp.ui.topheadlines


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.ActivityTopHeadlinesBinding
import com.gunishjain.newsapp.di.component.ActivityComponent
import com.gunishjain.newsapp.ui.base.BaseActivity
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlinesActivity :BaseActivity<TopHeadlinesViewModel,ActivityTopHeadlinesBinding>() {

    companion object {

        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"

        fun getStartIntent(context: Context, country: String): Intent {
            return Intent(context, TopHeadlinesActivity::class.java)
                .apply {
                    putExtra(EXTRAS_COUNTRY, country)
                }
        }

    }

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlinesAdapter

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun getViewBinding(): ActivityTopHeadlinesBinding {
        return ActivityTopHeadlinesBinding.inflate(layoutInflater)
    }

    private fun getIntentAndFetchData() {
        val country = intent.getStringExtra(EXTRAS_COUNTRY)
        country?.let {
            viewModel.fetchNews(country)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentAndFetchData()
    }

    override fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=topHeadlineAdapter
        }
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect{
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
                            Toast.makeText(this@TopHeadlinesActivity,it.message,Toast.LENGTH_LONG)
                                .show()
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

}