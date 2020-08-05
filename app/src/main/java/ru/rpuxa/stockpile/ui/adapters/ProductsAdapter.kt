package ru.rpuxa.stockpile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.rpuxa.stockpile.R
import ru.rpuxa.stockpile.databinding.ItemProductBinding
import ru.rpuxa.stockpile.model.Product
import ru.rpuxa.stockpile.model.Products
import ru.rpuxa.stockpile.ui.App
import ru.rpuxa.stockpile.ui.fragments.ProductsFragmentDirections
import ru.rpuxa.stockpile.viewmodels.EditProductViewModel
import javax.inject.Inject

class ProductsAdapter(
    private val controller: NavController,
    private val viewModel:EditProductViewModel,
    private val scope: CoroutineScope
) : BaseAdapter<Product, ItemProductBinding>(Diff) {

    @Inject
    lateinit var products: Products

    init {
        App.component.inject(this)
    }

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup) =
        ItemProductBinding.inflate(inflater, parent, false)

    override fun View.getViewHolder(binding: ItemProductBinding) = bind { product ->
        binding.price.text = getString(R.string.price, product.price)
        binding.name.text = product.name
        binding.image.setImageBitmap(products.get(product.type))
        binding.geo.isVisible = product.location != null
        binding.geo.setOnClickListener {
            controller.navigate(
                ProductsFragmentDirections.actionListFragmentToLocationFragment(product.location!!)
            )
        }
        binding.root.setOnClickListener {
            scope.launch {
                viewModel.load(product.id)
                controller.navigate(
                    ProductsFragmentDirections.actionListFragmentToEditDialog()
                )
            }
        }
    }

    private object Diff : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.name == newItem.name &&
                    oldItem.price == newItem.price &&
                    oldItem.type == newItem.type &&
                    oldItem.location == newItem.location
    }
}