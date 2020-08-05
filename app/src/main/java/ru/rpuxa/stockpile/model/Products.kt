package ru.rpuxa.stockpile.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ru.rpuxa.stockpile.R

class Products(private val res: Resources) {
    private var bitmaps: List<Bitmap> = emptyList()

    fun list(): List<Bitmap> {
        if (bitmaps.isEmpty()) {
            bitmaps = listOf(
                R.drawable.product1,
                R.drawable.product2,
                R.drawable.product3,
                R.drawable.product4,
                R.drawable.product5
            ).map {
                BitmapFactory.decodeResource(res, it)
            }
        }
        return bitmaps
    }

    fun get(index: Int): Bitmap {
        return list()[index]
    }
}