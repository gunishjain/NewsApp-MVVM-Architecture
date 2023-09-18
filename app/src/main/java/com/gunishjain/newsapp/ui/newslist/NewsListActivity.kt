package com.gunishjain.newsapp.ui.newslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.ActivityNewsListBinding
import com.gunishjain.newsapp.di.component.DaggerActivityComponent
import com.gunishjain.newsapp.di.module.ActivityModule
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListActivity : AppCompatActivity() {

    companion object {
        const val COUNTRY = "COUNTRY"
        const val SOURCE = "SOURCE"
        const val LANGUAGE = "LANGUAGE"

        fun getStartIntent(context: Context, country: String? = null, language: String? = null, source: String? = null): Intent {
            return Intent(context, NewsListActivity::class.java)
                .apply {
                    country?.let { putExtra(COUNTRY, country) }
                    language?.let { putExtra(LANGUAGE, language) }
                    source?.let { putExtra(SOURCE, source) }
                }
        }

    }


    @Inject
    lateinit var newsListViewModel: NewsListViewModel

    @Inject
    lateinit var newsListAdapter: TopHeadlinesAdapter

    private lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding= ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
        getIntentAndFetchData()
    }

    private fun getIntentAndFetchData() {

        val country = intent.getStringExtra(COUNTRY)
        val language = intent.getStringExtra(LANGUAGE)
        val source = intent.getStringExtra(SOURCE)

        language?.let {
            newsListViewModel.fetchNewsOnLanguage(it)
        }
        country?.let {
            newsListViewModel.fetchNewsOnCountry(it)
        }
        source?.let {
            newsListViewModel.fetchNewsOnSrc(it)
        }

    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter=newsListAdapter
        }
    }

    private fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collect {
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
                            Toast.makeText(this@NewsListActivity,it.message, Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }


    private fun renderList(articleList: List<Article>) {
        newsListAdapter.addArticles(articleList)
        newsListAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}