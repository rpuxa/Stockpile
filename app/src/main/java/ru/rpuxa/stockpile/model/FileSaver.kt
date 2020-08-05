package ru.rpuxa.stockpile.model

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

class FileSaver(
    private val fileName: String,
    private val productDao: ProductDao
) {

    suspend fun save() {
        withContext(Dispatchers.IO) {
            val products = productDao.getAll()
            DataOutputStream(FileOutputStream(fileName)).use { output ->
                output.writeInt(products.size)
                products.forEach {
                    with(it) {
                        output.writeUTF(name)
                        output.writeDouble(price)
                        output.writeInt(type)
                        output.writeDouble(location?.latitude ?: Double.NaN)
                        output.writeDouble(location?.longitude ?: Double.NaN)
                    }
                }
            }
        }
    }

    suspend fun load(): Boolean {
        try {
            withContext(Dispatchers.IO) {
                val list = DataInputStream(FileInputStream(fileName)).use { input ->
                    (0 until input.readInt()).map {
                        val name = input.readUTF()
                        val price = input.readDouble()
                        val type = input.readInt()
                        val lat = input.readDouble()
                        val lng = input.readDouble()
                        val location = if (lat.isNaN() || lng.isNaN()) {
                            null
                        } else {
                            LatLng(lat, lng)
                        }
                        Product(0, name, price, type, location)
                    }
                }
                productDao.clear()
                productDao.insertAll(list)
            }
            return true
        } catch (e: IOException) {
            return false
        }
    }
}