package com.gunishjain.newsapp.ui.selections

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.databinding.ActivityLanguageSelectionBinding
import com.gunishjain.newsapp.databinding.SourceItemLayoutBinding
import com.gunishjain.newsapp.di.component.ActivityComponent
import com.gunishjain.newsapp.ui.base.BaseActivity
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.newslist.NewsListActivity
import com.gunishjain.newsapp.utils.genericrecyclerview.BaseAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageSelectionActivity : BaseActivity<SelectionsViewModel,ActivityLanguageSelectionBinding>() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LanguageSelectionActivity::class.java)
        }
    }

    @Inject
    lateinit var languageAdapter: BaseAdapter<Language>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onButtonClick()
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun getViewBinding(): ActivityLanguageSelectionBinding {
        return ActivityLanguageSelectionBinding.inflate(layoutInflater)
    }

    private fun onButtonClick() {
        binding.proceed.setOnClickListener {
            proceedWithSelectedLanguages()
        }
    }

    private fun proceedWithSelectedLanguages() {

        var selectedLanguages = languageAdapter.getSelectedItems()
        if (selectedLanguages.size in 1..2) {
            // Create an intent and pass the selected language IDs to the NewsListActivity
            val languageIds = selectedLanguages.map { it.languageId }
            startActivity(NewsListActivity.getStartIntent(this, languages = languageIds))
        } else {
           Toast.makeText(this@LanguageSelectionActivity,
               "Select At most Two Languages",Toast.LENGTH_SHORT).show()
        }
    }

    override fun setupUI() {

        languageAdapter.expressionViewHolderBinding = { eachItem,viewBinding->

            val view = viewBinding as SourceItemLayoutBinding
            view.tvSrc.text = eachItem.languageName
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

    override fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateLanguage.collect {
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

    override fun onStop() {
        super.onStop()
        languageAdapter.clearSelectedItems()
    }

}