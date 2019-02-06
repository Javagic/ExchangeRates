package com.javagic.exchangerates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewAdapter<T>(
    @LayoutRes private val itemRes: Int,
    diffCallback: DiffUtil.ItemCallback<T> = SimpleDiffCallback(),
    private val holderFactory: (View) -> ViewHolder<T>
) : ListAdapter<T, RecyclerViewAdapter.ViewHolder<T>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> =
        holderFactory.invoke(
            LayoutInflater.from(parent.context).inflate(itemRes, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) =
        holder.bind(getItem(position))


    abstract class ViewHolder<T>(protected val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }


    open class SimpleDiffCallback<T> : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    }
}