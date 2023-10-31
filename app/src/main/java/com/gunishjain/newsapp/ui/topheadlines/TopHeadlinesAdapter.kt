package com.gunishjain.newsapp.ui.topheadlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.databinding.TopHeadlinesItemLayoutBinding
import com.gunishjain.newsapp.utils.ItemClickListener

class TopHeadlinesAdapter(
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<TopHeadlinesAdapter.HeadlineViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Article>

    class HeadlineViewHolder(val binding: TopHeadlinesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, itemClickListener: ItemClickListener<Article>) {
            binding.tvTitle.text = article.title
            binding.tvDesc.text = article.description
            binding.tvSrc.text = article.source.name
            Glide.with(binding.imgBanner.context)
                .load(article.imageUrl)
                .into(binding.imgBanner)
            itemView.setOnClickListener {
                itemClickListener(article)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        return HeadlineViewHolder(
            TopHeadlinesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) =
        holder.bind(articleList[position], itemClickListener)


    fun addArticles(list: List<Article>) {
        articleList.addAll(list)
    }

    fun updateData(list: List<Article>) {
        articleList.clear()
        articleList.addAll(list)
    }

}