package com.gunishjain.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunishjain.newsapp.databinding.ActivityMainBinding
import com.gunishjain.newsapp.ui.search.SearchNewsActivity
import com.gunishjain.newsapp.ui.selections.CountrySelectionActivity
import com.gunishjain.newsapp.ui.selections.LanguageSelectionActivity
import com.gunishjain.newsapp.ui.sources.NewsSourceActivity
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            topHeadlines.setOnClickListener {
                val intent = Intent(it.context, TopHeadlinesActivity::class.java)
                startActivity(intent)
            }

            newsSrc.setOnClickListener {
                val intent = Intent(it.context, NewsSourceActivity::class.java)
                startActivity(intent)
            }

            countries.setOnClickListener {
                val intent = Intent(it.context, CountrySelectionActivity::class.java)
                startActivity(intent)
            }

            lang.setOnClickListener {
                val intent = Intent(it.context, LanguageSelectionActivity::class.java)
                startActivity(intent)
            }

            search.setOnClickListener {
                val intent = Intent(it.context, SearchNewsActivity::class.java)
                startActivity(intent)
            }

        }

    }
}