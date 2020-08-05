package ru.rpuxa.stockpile.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @get:Query("SELECT * FROM products")
    val liveData: LiveData<List<Product>>

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun get(id: Int): Product

    @Insert
    fun insertAll(list: List<Product>)

    @Query("DELETE FROM products")
    fun clear()
}

