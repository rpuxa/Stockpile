package ru.rpuxa.stockpile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.rpuxa.stockpile.appActivity
import ru.rpuxa.stockpile.databinding.FragmentMapBinding
import ru.rpuxa.stockpile.viewModel
import ru.rpuxa.stockpile.viewmodels.LocationViewModel


class GetLocationFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: LocationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState?.getBundle(MAP_KEY))
        binding.accept.setOnClickListener {
            binding.mapView.getMapAsync {
                viewModel.saveLocation(it.cameraPosition.target)
                appActivity.controller.navigateUp()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_KEY, mapViewBundle)
        }
        binding.mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }


    companion object {
        private const val MAP_KEY = "MapKey"
    }
}