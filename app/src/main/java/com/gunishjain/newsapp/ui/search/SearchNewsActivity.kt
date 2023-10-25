package com.gunishjain.newsapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.ActivitySearchNewsBinding
import com.gunishjain.newsapp.di.component.DaggerActivityComponent
import com.gunishjain.newsapp.di.module.ActivityModule
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesAdapter
import com.gunishjain.newsapp.utils.getQueryTextChangeStateFlow
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchNewsActivity : AppCompatActivity() {

    @Inject
    lateinit var searchNewsViewModel: SearchNewsViewModel

    @Inject
    lateinit var searchNewsAdapter: TopHeadlinesAdapter

    private lateinit var binding: ActivitySearchNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding=ActivitySearchNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()

    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter=searchNewsAdapter
        }


       binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
           android.widget.SearchView.OnQueryTextListener {
           override fun onQueryTextSubmit(query: String?): Boolean {
               return true
           }

           override fun onQueryTextChange(newText: String?): Boolean {
               if (newText != null) {
                   searchNewsViewModel.onQuerySearch(newText)
               }
               return true
           }
       })

    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchNewsViewModel.uiState.collect {
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
                            Toast.makeText(this@SearchNewsActivity,it.message, Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        searchNewsAdapter.updateData(articleList)
        searchNewsAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

}


