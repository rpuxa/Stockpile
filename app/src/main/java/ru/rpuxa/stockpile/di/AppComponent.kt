package ru.rpuxa.stockpile.di

import dagger.Component
import ru.rpuxa.stockpile.model.ProductDao
import ru.rpuxa.stockpile.ui.fragments.EditProductFragment
import ru.rpuxa.stockpile.ui.adapters.ProductsAdapter
import ru.rpuxa.stockpile.viewmodels.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelsModule::class
    ]
)
interface AppComponent {
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(viewModelFactory: EditProductFragment)
    fun inject(productsAdapter: ProductsAdapter)

    val productDao: ProductDao

}