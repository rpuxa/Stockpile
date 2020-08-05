package ru.rpuxa.stockpile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rpuxa.stockpile.R
import ru.rpuxa.stockpile.appActivity
import ru.rpuxa.stockpile.viewModel
import ru.rpuxa.stockpile.viewmodels.EditProductViewModel
import androidx.lifecycle.observe
import kotlinx.coroutines.launch
import ru.rpuxa.stockpile.databinding.FragmentEditBinding
import ru.rpuxa.stockpile.model.Products
import ru.rpuxa.stockpile.ui.App
import ru.rpuxa.stockpile.ui.adapters.ImageAdapter
import ru.rpuxa.stockpile.viewmodels.LocationViewModel
import ru.rpuxa.stockpile.viewmodels.ProductsViewModel
import javax.inject.Inject

class EditProductFragment : Fragment() {

    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    private val viewModel: EditProductViewModel by viewModel()
    private val productsViewModel: ProductsViewModel by viewModel()
    private val locationViewModel: LocationViewModel by viewModel()

    @Inject
    lateinit var products: Products

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        App.component.inject(this)
        val adapter = ImageAdapter(viewModel)
        binding.images.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.images.adapter = adapter
        adapter.submitList(products.list())
        binding.toolbar.setNavigationIcon(R.drawable.back)
        binding.toolbar.setNavigationOnClickListener {
            appActivity.onBackPressed()
        }
        binding.toolbar.title = getString(
            if (viewModel.id == 0) R.string.creation else R.string.product_editing
        )
        viewModel.name.observe(viewLifecycleOwner) {
            if (binding.name.text.toString() != it)
                binding.name.setText(it)
        }
        viewModel.price.observe(viewLifecycleOwner) {
            if (binding.price.text.toString() != it)
                binding.price.setText(it)
        }
        viewModel.imageType.observe(viewLifecycleOwner) {
            val old = adapter.frameIndex
            adapter.frameIndex = it
            adapter.notifyItemChanged(old)
            adapter.notifyItemChanged(it)
        }
        viewModel.location.observe(viewLifecycleOwner) {
            binding.location.text = getString(
                R.string.location_template,
                if (it == null) {
                    getString(R.string.location_is_undefined)
                } else {
                    getString(R.string.location_is_defined, it.latitude, it.longitude)
                }
            )
        }

        binding.editLocation.setOnClickListener {
            locationViewModel.setLocationListener {
                viewModel.location.value = it
            }
            appActivity.controller.navigate(
                EditProductFragmentDirections.actionEditDialogToMapFragment(
                    viewModel.location.value
                )
            )
        }

        binding.name.addTextChangedListener {
            viewModel.name.value = it.toString()
        }

        binding.price.addTextChangedListener {
            viewModel.price.value = it.toString()
        }

        binding.accept.setOnClickListener {
            lifecycleScope.launch {
                viewModel.save()
                appActivity.controller.navigateUp()
            }
        }

        if (viewModel.id == 0) {
            binding.delete.isVisible = false
        } else {
            binding.delete.setOnClickListener {
                productsViewModel.removeProduct(viewModel.id)
                appActivity.controller.navigateUp()
            }
        }
    }
}