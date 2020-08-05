package ru.rpuxa.stockpile.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.rpuxa.stockpile.viewmodels.EditProductViewModel
import ru.rpuxa.stockpile.viewmodels.ProductsViewModel
import ru.rpuxa.stockpile.viewmodels.LocationViewModel

@Module
abstract class ViewModelsModule {


    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    abstract fun products(v: ProductsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun map(v: LocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProductViewModel::class)
    abstract fun edit(v: EditProductViewModel): ViewModel
}