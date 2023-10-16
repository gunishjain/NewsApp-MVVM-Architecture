package com.gunishjain.newsapp.ui.selections

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
import com.gunishjain.newsapp.databinding.ActivityCountrySelectionBinding
import com.gunishjain.newsapp.databinding.SourceItemLayoutBinding
import com.gunishjain.newsapp.di.component.DaggerActivityComponent
import com.gunishjain.newsapp.di.module.ActivityModule
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.newslist.NewsListActivity
import com.gunishjain.newsapp.utils.genericrecyclerview.BaseAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountrySelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var selectionVM: SelectionsViewModel

    @Inject
    lateinit var countryAdapter: BaseAdapter<Country>


    private lateinit var binding : ActivityCountrySelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
        onButtonClick()
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
            val countryIds = selectedCountries.map { it.countryId }
            startActivity(NewsListActivity.getStartIntent(this, countries = countryIds))
        } else {
            Toast.makeText(this@CountrySelectionActivity,
                "Select At most Two Countries",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUI() {

        countryAdapter.expressionViewHolderBinding = { eachItem,viewBinding->
            var view = viewBinding as SourceItemLayoutBinding
            view.tvSrc.text = eachItem.countryName

        }

        countryAdapter.expressionOnCreateViewHolder = { viewGroup->

            SourceItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=countryAdapter
        }
    }

    private fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectionVM.uiStateCountry.collect {
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


    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    override fun onStop() {
        super.onStop()
        countryAdapter.clearSelectedItems()
    }
}