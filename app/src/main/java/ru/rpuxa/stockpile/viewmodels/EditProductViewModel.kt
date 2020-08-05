package ru.rpuxa.stockpile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import ru.rpuxa.stockpile.model.Product
import ru.rpuxa.stockpile.model.ProductDao
import javax.inject.Inject

class EditProductViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    var id = 0
        private set
    val name = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val location = MutableLiveData<LatLng>()
    val imageType = MutableLiveData<Int>()

    suspend fun load(id: Int) {
        if (id == 0) {
            name.value = "Product"
            price.value = "100"
            location.value = null
            imageType.value = 0
        } else {
            val product = productDao.get(id)
            name.value = product.name
            price.value = product.price.toString()
            location.value = product.location
            imageType.value = product.type
        }
        this.id = id
    }

    suspend fun save() {
        productDao.insert(
            Product(
                id,
                name.value!!,
                price.value!!.toDouble(),
                imageType.value!!,
                location.value
            )
        )
    }
}