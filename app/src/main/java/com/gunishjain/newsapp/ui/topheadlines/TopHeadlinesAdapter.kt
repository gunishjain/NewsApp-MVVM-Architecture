package com.gunishjain.newsapp.ui.topheadlines

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.TopHeadlinesItemLayoutBinding

class TopHeadlinesAdapter (private val articleList: ArrayList<Article>
): RecyclerView.Adapter<TopHeadlinesAdapter.HeadlineViewHolder>() {

    class HeadlineViewHolder(private val binding: TopHeadlinesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(article: Article){
                binding.tvTitle.text=article.title
                binding.tvDesc.text=article.description
                binding.tvSrc.text=article.source.name
                Glide.with(binding.imgBanner.context)
                    .load(article.imageUrl)
                    .into(binding.imgBanner)
                itemView.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        return HeadlineViewHolder(TopHeadlinesItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) =
        holder.bind(articleList[position])

    fun addArticles(list: List<Article>) {
        articleList.addAll(list)
    }
    fun updateData(list: List<Article>) {
        articleList.clear()
        articleList.addAll(list)
    }

}