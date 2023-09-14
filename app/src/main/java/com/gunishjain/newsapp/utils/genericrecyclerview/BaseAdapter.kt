package com.gunishjain.newsapp.utils.genericrecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var listOfItems:List<T>? = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var expressionViewHolderBinding: ((T,ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder:((ViewGroup)-> ViewBinding)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }?.let { BaseViewHolder(it, expressionViewHolderBinding!!) }!!    }

    override fun getItemCount(): Int {
        return listOfItems!!.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(listOfItems!![position])
    }
}