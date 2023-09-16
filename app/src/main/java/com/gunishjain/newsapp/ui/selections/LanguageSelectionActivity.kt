package com.gunishjain.newsapp.ui.selections

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.databinding.ActivityLanguageSelectionBinding
import com.gunishjain.newsapp.databinding.SourceItemLayoutBinding
import com.gunishjain.newsapp.di.component.DaggerActivityComponent
import com.gunishjain.newsapp.di.module.ActivityModule
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.newslist.NewsListActivity
import com.gunishjain.newsapp.utils.genericrecyclerview.BaseAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageSelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var selectionVM: SelectionsViewModel

    @Inject
    lateinit var languageAdapter: BaseAdapter<Language>

    private lateinit var binding: ActivityLanguageSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {

        languageAdapter.expressionViewHolderBinding = { eachItem,viewBinding->
            var view = viewBinding as SourceItemLayoutBinding
            view.tvSrc.text = eachItem.languageName

            view.root.setOnClickListener {
                val intent = Intent(view.root.context, NewsListActivity::class.java)
                intent.putExtra("languageId", eachItem.languageId)
                startActivity(intent)
            }

        }

        languageAdapter.expressionOnCreateViewHolder = { viewGroup->

            SourceItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context),
                viewGroup, false)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            adapter=languageAdapter
        }
    }

    private fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectionVM.uiStateLanguage.collect {
                    when (it) {
                        is UiState.Success -> {
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.apply {
                                recyclerView.visibility = View.GONE
                            }
                        }
                        is UiState.Error -> {
                            Toast.makeText(
                                this@LanguageSelectionActivity,
                                it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }
        }
    }

    private fun renderList(data: List<Language>) {
        languageAdapter.listOfItems =data
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}