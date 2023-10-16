package com.gunishjain.newsapp.utils.genericrecyclerview

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T>
internal constructor(private val binding:ViewBinding,
                     private val expression:(T,ViewBinding)->Unit)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T,isSelected: Boolean){
        expression(item,binding)

        if(isSelected){
            binding.root.setBackgroundColor(Color.YELLOW)
        } else {
            binding.root.setBackgroundColor(Color.WHITE)
        }

    }
}