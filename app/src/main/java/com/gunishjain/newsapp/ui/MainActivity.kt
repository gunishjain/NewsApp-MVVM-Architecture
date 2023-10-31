package com.gunishjain.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gunishjain.newsapp.databinding.ActivityMainBinding
import com.gunishjain.newsapp.ui.search.SearchNewsActivity
import com.gunishjain.newsapp.ui.selections.CountrySelectionActivity
import com.gunishjain.newsapp.ui.selections.LanguageSelectionActivity
import com.gunishjain.newsapp.ui.sources.NewsSourceActivity
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesActivity
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            topHeadlines.setOnClickListener {
                startActivity(TopHeadlinesActivity.getStartIntent(this@MainActivity, COUNTRY))
            }

            newsSrc.setOnClickListener {
                startActivity(NewsSourceActivity.getStartIntent(this@MainActivity))
            }

            countries.setOnClickListener {
                startActivity(CountrySelectionActivity.getStartIntent(this@MainActivity))
            }

            lang.setOnClickListener {
                startActivity(LanguageSelectionActivity.getStartIntent(this@MainActivity))
            }

            search.setOnClickListener {
                startActivity(SearchNewsActivity.getStartIntent(this@MainActivity))
            }

        }

    }
}