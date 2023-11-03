package com.gunishjain.newsapp.ui.sources


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.databinding.ActivityNewsSourceBinding
import com.gunishjain.newsapp.ui.base.BaseActivity
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.newslist.NewsListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsSourceActivity : BaseActivity<NewsSourceViewModel, ActivityNewsSourceBinding>(
    NewsSourceViewModel::class.java
) {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewsSourceActivity::class.java)
        }
    }

    @Inject
    lateinit var sourceAdapter: NewsSourceAdapter

    override fun getViewBinding(): ActivityNewsSourceBinding {
        return ActivityNewsSourceBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = sourceAdapter
        }

        sourceAdapter.itemClickListener = {
            startActivity(NewsListActivity.getStartIntent(this, source = it.id))
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
                            binding.progressBar.visibility = View.VISIBLE
                            Toast.makeText(this@NewsSourceActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(sourceList: List<Source>) {
        sourceAdapter.addSources(sourceList)
        sourceAdapter.notifyDataSetChanged()
    }

}