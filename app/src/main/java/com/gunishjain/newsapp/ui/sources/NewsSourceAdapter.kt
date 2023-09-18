package com.gunishjain.newsapp.ui.sources

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.databinding.SourceItemLayoutBinding
import com.gunishjain.newsapp.ui.newslist.NewsListActivity

class NewsSourceAdapter (private val sourceList: ArrayList<Source>):
    RecyclerView.Adapter<NewsSourceAdapter.SourceViewHolder>() {

        class SourceViewHolder(private val binding: SourceItemLayoutBinding)
            : RecyclerView.ViewHolder(binding.root) {
                fun bind(source: Source){
                    binding.tvSrc.text = source.name

                    itemView.setOnClickListener {

                       itemView.context.startActivity(
                           NewsListActivity.getStartIntent(itemView.context, source=source.id)
                       )

                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(SourceItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(sourceList[position])
    }

    fun addSources(list: List<Source>) {
        sourceList.addAll(list)
    }

}