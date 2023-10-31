package com.gunishjain.newsapp.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.databinding.SourceItemLayoutBinding
import com.gunishjain.newsapp.utils.ItemClickListener

class NewsSourceAdapter(private val sourceList: ArrayList<Source>) :
    RecyclerView.Adapter<NewsSourceAdapter.SourceViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Source>

    class SourceViewHolder(private val binding: SourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source, itemClickListener: ItemClickListener<Source>) {
            binding.tvSrc.text = source.name
            itemView.setOnClickListener {
                itemClickListener(source)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(
            SourceItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(sourceList[position], itemClickListener)
    }

    fun addSources(list: List<Source>) {
        sourceList.addAll(list)
    }

}