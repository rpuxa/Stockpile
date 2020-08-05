package ru.rpuxa.stockpile.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "products")
@TypeConverters(LocationConverter::class)
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val price: Double,
    val type:Int,
    val location: LatLng?
)