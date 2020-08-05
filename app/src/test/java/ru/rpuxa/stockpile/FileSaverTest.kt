package ru.rpuxa.stockpile

import com.google.android.gms.maps.model.LatLng
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import ru.rpuxa.stockpile.model.FileSaver
import ru.rpuxa.stockpile.model.Product
import ru.rpuxa.stockpile.model.ProductDao
import java.io.File

class FileSaverTest {

    private val list = (0..50).map {
        Product(
            0,
            it.toString(),
            it.toDouble(),
            it,
            if (it == 0) null else LatLng(it.toDouble(), it.toDouble())
        )
    }

    private val fileName = "test file"
    private val product = mockk<ProductDao> {
        coEvery { getAll() } returns list
        coEvery { clear() } returns Unit
        coEvery { insertAll(any()) } returns Unit
    }

    @Test
    fun `Save and load test`() {
        runBlocking {
            val fileSaver = FileSaver(fileName, product)
            fileSaver.save()
            assertEquals(true, File(fileName).exists())
            assertEquals(true, fileSaver.load())
            coVerify { product.insertAll(list) }
            File(fileName).delete()
            assertEquals(false, fileSaver.load())
        }
    }
}