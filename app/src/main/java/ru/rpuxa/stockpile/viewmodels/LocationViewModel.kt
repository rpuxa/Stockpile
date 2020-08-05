package ru.rpuxa.stockpile.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class LocationViewModel @Inject constructor() : ViewModel() {

    private var locationListener: ((LatLng) -> Unit)? = null

    fun setLocationListener(block: (LatLng) -> Unit) {
        locationListener = block
    }

    fun saveLocation(loc: LatLng) {
        locationListener?.invoke(loc)
        locationListener = null
    }
}