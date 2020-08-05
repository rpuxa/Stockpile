package ru.rpuxa.stockpile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, Binding : ViewBinding>(diff: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseAdapter<T, Binding>.BaseViewHolder>(diff) {

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun Context.bind(item: T)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder) {
            itemView.context.bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflate(inflater, parent)
        val view = binding.root
        return view.getViewHolder(binding)
    }

    protected abstract fun inflate(inflater: LayoutInflater, parent: ViewGroup): Binding

    protected abstract fun View.getViewHolder(binding: Binding): BaseViewHolder

    protected inline fun View.bind(crossinline block: Context.(T) -> Unit) =
        object : BaseViewHolder(this) {
            override fun Context.bind(item: T) {
                block(item)
            }
        }
}