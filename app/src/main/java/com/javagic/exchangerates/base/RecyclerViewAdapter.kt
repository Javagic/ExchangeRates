package com.javagic.exchangerates.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewAdapter<T>(
    @LayoutRes private val itemRes: Int,
    private val holderFactory: (View) -> ViewHolder<T>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder<T>>() {
    val data: MutableList<T> = ArrayList()

    fun submitList(value: List<T>) {
        data.clear()
        data.addAll(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> =
        holderFactory.invoke(
            LayoutInflater.from(parent.context).inflate(itemRes, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) =
        holder.bind(data[position])


    abstract class ViewHolder<T>(protected val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    override fun getItemCount(): Int = data.size

}