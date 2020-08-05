package ru.rpuxa.stockpile.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.rpuxa.stockpile.CombinedLiveData
import ru.rpuxa.stockpile.SingleLiveEvent
import ru.rpuxa.stockpile.model.FileSaver
import ru.rpuxa.stockpile.model.Product
import ru.rpuxa.stockpile.model.ProductDao
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productDao: ProductDao,
    private val fileSaver: FileSaver
) : ViewModel() {

    val filter = MutableLiveData("")
    val products: LiveData<List<Product>> = CombinedLiveData(productDao.liveData, filter) { list, filter ->
        if (list == null || filter == null) return@CombinedLiveData emptyList<Product>()
        if (filter.isBlank()) return@CombinedLiveData list
        list.filter {
            it.name.contains(filter, true)
        }
    }
    val action = SingleLiveEvent<Action>()

    fun removeProduct(id: Int) {
        viewModelScope.launch {
            productDao.delete(id)
        }
    }

    fun saveToFile() {
        viewModelScope.launch {
            fileSaver.save()
            action.value = Action.SUCCESSFULLY_SAVED
        }
    }

    fun loadFromFile() {
        viewModelScope.launch {
            action.value = if (fileSaver.load()) Action.SUCCESSFULLY_LOADED else Action.ERROR
        }
    }

    enum class Action {
        SUCCESSFULLY_SAVED,
        SUCCESSFULLY_LOADED,
        ERROR
    }
}