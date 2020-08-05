package ru.rpuxa.stockpile.ui.fragments

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ru.rpuxa.stockpile.databinding.FragmentListBinding
import ru.rpuxa.stockpile.viewModel
import ru.rpuxa.stockpile.viewmodels.ProductsViewModel
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.rpuxa.stockpile.R
import ru.rpuxa.stockpile.appActivity
import ru.rpuxa.stockpile.model.Product
import ru.rpuxa.stockpile.ui.adapters.ProductsAdapter
import ru.rpuxa.stockpile.viewmodels.EditProductViewModel

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ProductsViewModel by viewModel()
    private val editViewModel: EditProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ProductsAdapter(
            appActivity.controller,
            editViewModel,
            lifecycleScope
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.products.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyListLabel.isVisible = list.isEmpty()
        }
        binding.addProduct.setOnClickListener {
            lifecycleScope.launch {
                editViewModel.load(0)
                appActivity.controller.navigate(
                    ProductsFragmentDirections.actionListFragmentToEditDialog()
                )
            }
        }

        binding.toolbar.inflateMenu(R.menu.toolbar_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PERMISSION_GRANTED
                    ) {
                        viewModel.saveToFile()
                    } else {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            WRITE_PERMISSION_CODE
                        )
                    }
                }
                R.id.load -> {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PERMISSION_GRANTED
                    ) {
                        viewModel.loadFromFile()
                    } else {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            READ_PERMISSION_CODE
                        )
                    }
                }
            }
            false
        }

        viewModel.action.observe(viewLifecycleOwner) {
            val text = when (it) {
                ProductsViewModel.Action.SUCCESSFULLY_SAVED -> getString(R.string.successfully_saved)
                ProductsViewModel.Action.SUCCESSFULLY_LOADED -> getString(R.string.successfully_loaded)
                ProductsViewModel.Action.ERROR -> getString(R.string.error)
            }
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }

        binding.search.addTextChangedListener {
            viewModel.filter.value = it.toString()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == WRITE_PERMISSION_CODE) {
            if (grantResults[0] != PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.accept_to_save),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.saveToFile()
            }
        } else if (requestCode == READ_PERMISSION_CODE) {
            if (grantResults[0] != PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.accept_to_load),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.loadFromFile()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val WRITE_PERMISSION_CODE = 123
        private const val READ_PERMISSION_CODE = 124
    }
}