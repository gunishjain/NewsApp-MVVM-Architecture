package com.gunishjain.newsapp.ui.newslist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.ActivityNewsListBinding
import com.gunishjain.newsapp.ui.base.BaseActivity
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class NewsListActivity : BaseActivity<NewsListViewModel, ActivityNewsListBinding>
    (NewsListViewModel::class.java) {

    companion object {
        const val COUNTRY = "COUNTRY"
        const val SOURCE = "SOURCE"
        const val LANGUAGE = "LANGUAGE"

        fun getStartIntent(
            context: Context,
            countries: List<String?>? = null,
            languages: List<String?>? = null,
            source: String? = null
        ): Intent {
            return Intent(context, NewsListActivity::class.java)
                .apply {
                    countries?.let { putStringArrayListExtra(COUNTRY, ArrayList(countries)) }
                    languages?.let { putStringArrayListExtra(LANGUAGE, ArrayList(languages)) }
                    source?.let { putExtra(SOURCE, source) }
                }
        }

    }

    @Inject
    lateinit var newsListAdapter: TopHeadlinesAdapter

    override fun getViewBinding(): ActivityNewsListBinding {
        return ActivityNewsListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentAndFetchData()
    }

    private fun getIntentAndFetchData() {

        val country = intent.getStringArrayListExtra(COUNTRY)
        val language = intent.getStringArrayListExtra(LANGUAGE)
        val source = intent.getStringExtra(SOURCE)

        language?.let {
            viewModel.fetchNewsOnLanguage(it)
        }
        country?.let {
            viewModel.fetchNewsOnCountry(it)
        }
        source?.let {
            viewModel.fetchNewsOnSrc(it)
        }

    }

    override fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsListAdapter
        }

        newsListAdapter.itemClickListener = {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(it.url))
        }

    }

    override fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
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
                            Toast.makeText(this@NewsListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
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


}