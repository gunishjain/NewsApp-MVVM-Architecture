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
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.databinding.ActivityCountrySelectionBinding
import com.gunishjain.newsapp.databinding.SourceItemLayoutBinding
import com.gunishjain.newsapp.di.component.ActivityComponent
import com.gunishjain.newsapp.ui.base.BaseActivity
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.newslist.NewsListActivity
import com.gunishjain.newsapp.ui.base.genericrecyclerview.BaseAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountrySelectionActivity : BaseActivity<SelectionsViewModel,ActivityCountrySelectionBinding>() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CountrySelectionActivity::class.java)
        }
    }

    @Inject
    lateinit var countryAdapter: BaseAdapter<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onButtonClick()
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun getViewBinding(): ActivityCountrySelectionBinding {
        return ActivityCountrySelectionBinding.inflate(layoutInflater)
    }

    private fun onButtonClick() {
        binding.proceed.setOnClickListener {
            proceedWithSelectedCountries()
        }
    }

    private fun proceedWithSelectedCountries() {
        val selectedCountries = countryAdapter.getSelectedItems()
        if (selectedCountries.size in 1..2) {
            // Create an intent and pass the selected language IDs to the NewsListActivity
            val countryIds = selectedCountries.map { it.id }
            startActivity(NewsListActivity.getStartIntent(this, countries = countryIds))
        } else {
            Toast.makeText(this@CountrySelectionActivity,
                "Select At most Two Countries",Toast.LENGTH_SHORT).show()
        }
    }

    override fun setupUI() {

        countryAdapter.expressionViewHolderBinding = { eachItem,viewBinding->
            val view = viewBinding as SourceItemLayoutBinding
            view.tvSrc.text = eachItem.name

        }

        countryAdapter.expressionOnCreateViewHolder = { viewGroup->

            SourceItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=countryAdapter
        }
    }

    override fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateCountry.collect {
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
                                this@CountrySelectionActivity,
                                it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }
        }
    }

    private fun renderList(data: List<Country>) {
        countryAdapter.listOfItems =data
    }

    override fun onStop() {
        super.onStop()
        countryAdapter.clearSelectedItems()
    }
}