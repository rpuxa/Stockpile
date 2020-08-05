package ru.rpuxa.stockpile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import ru.rpuxa.stockpile.databinding.FragmentLocationBinding

class ShowLocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private val args: ShowLocationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.onCreate(savedInstanceState?.getBundle(MAP_KEY))
        binding.root.getMapAsync {
            it.moveCamera(CameraUpdateFactory.newLatLng(args.location))
            it.addMarker(MarkerOptions().position(args.location))
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_KEY, mapViewBundle)
        }
        binding.root.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        binding.root.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.root.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.root.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.root.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.root.onLowMemory()
    }


    companion object {
        private const val MAP_KEY = "MapKey"
    }
}