package com.gunishjain.newsapp.ui.base.genericrecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.Language

class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val selectedItems = mutableSetOf<T>()

    var listOfItems: List<T>? = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var expressionViewHolderBinding: ((T, ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }
            ?.let { BaseViewHolder(it, expressionViewHolderBinding!!) }!!
    }

    override fun getItemCount(): Int {
        return listOfItems!!.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {

        val item = listOfItems!![position]

        val isSelected = item != null && selectedItems.contains(item)

        holder.bind(item, isSelected)

        holder.itemView.setOnClickListener {
            item?.let {
                if (selectedItems.contains(it)) {
                    selectedItems.remove(it)
                } else {
                    selectedItems.add(it)
                }
                notifyItemChanged(position)
            }
        }
    }


    fun getSelectedItems(): List<T> {
        return selectedItems.toList()
    }

    fun clearSelectedItems() {
        selectedItems.clear()
        notifyDataSetChanged()
    }
}