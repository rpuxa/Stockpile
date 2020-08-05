package ru.rpuxa.stockpile.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.rpuxa.stockpile.R
import ru.rpuxa.stockpile.databinding.ImageItemBinding
import ru.rpuxa.stockpile.viewmodels.EditProductViewModel

class ImageAdapter(private val viewModel: EditProductViewModel) :
    BaseAdapter<Bitmap, ImageItemBinding>(Diff) {

    var frameIndex = 0

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup) =
        ImageItemBinding.inflate(inflater, parent, false)

    override fun View.getViewHolder(binding: ImageItemBinding) = object : BaseViewHolder(this) {
        override fun Context.bind(item: Bitmap) {
            binding.root.setImage(item)
            binding.root.hasFrame = adapterPosition == frameIndex
            binding.root.setOnClickListener {
                viewModel.imageType.value = adapterPosition
            }
            R.anim.nav_default_enter_anim
        }
    }

    private object Diff : DiffUtil.ItemCallback<Bitmap>() {
        override fun areItemsTheSame(oldItem: Bitmap, newItem: Bitmap) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Bitmap, newItem: Bitmap) =
            areItemsTheSame(
                oldItem,
                newItem
            )
    }
}