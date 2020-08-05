package ru.rpuxa.stockpile.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Product::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {

    abstract val productDao: ProductDao

    companion object {
        fun create(context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, "database.db")
                .build()
        }
    }
}