package ru.rpuxa.stockpile.model

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class LocationConverter {

    @TypeConverter
    fun LatLng?.toStr() = if (this == null) "" else "$latitude,$longitude"

    @TypeConverter
    fun String.toLatLng(): LatLng? {
        if (isEmpty()) return null
        val (lat, lng) = split(',')
        return LatLng(lat.toDouble(), lng.toDouble())
    }
}