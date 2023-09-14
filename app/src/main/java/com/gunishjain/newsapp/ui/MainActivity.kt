package com.gunishjain.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunishjain.newsapp.databinding.ActivityMainBinding
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

        binding.topHeadlines.setOnClickListener {
            val intent = Intent(this,TopHeadlinesActivity::class.java)
            startActivity(intent)
        }

        binding.newsSrc.setOnClickListener {
            val intent = Intent(this,NewsSourceActivity::class.java)
            startActivity(intent)
        }

        binding.countries.setOnClickListener {
            val intent = Intent(this,CountrySelectionActivity::class.java)
            startActivity(intent)
        }

        binding.lang.setOnClickListener {
            val intent = Intent(this,LanguageSelectionActivity::class.java)
            startActivity(intent)
        }

    }
}