package ru.rpuxa.stockpile.di

import android.content.Context
import android.content.res.Resources
import android.os.Environment
import dagger.Module
import dagger.Provides
import ru.rpuxa.stockpile.model.DataBase
import ru.rpuxa.stockpile.model.FileSaver
import ru.rpuxa.stockpile.model.ProductDao
import ru.rpuxa.stockpile.model.Products
import ru.rpuxa.stockpile.ui.App
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    fun context(): Context = app

    @Provides
    fun resources(): Resources = app.resources

    @Provides
    @Singleton
    fun database(context: Context) = DataBase.create(context)

    @Provides
    fun productDao(db: DataBase) = db.productDao

    @Provides
    @Singleton
    fun products(resources: Resources) = Products(resources)

    @Provides
    @Singleton
    fun fileSaver(productDao: ProductDao) = FileSaver(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/products.dat",
        productDao
    )
}