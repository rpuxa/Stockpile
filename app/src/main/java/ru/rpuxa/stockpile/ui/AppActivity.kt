package ru.rpuxa.stockpile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.rpuxa.stockpile.R

class AppActivity : AppCompatActivity() {

    val controller by lazy { findNavController(R.id.content) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onSupportNavigateUp() =
        controller.navigateUp() || super.onSupportNavigateUp()
}