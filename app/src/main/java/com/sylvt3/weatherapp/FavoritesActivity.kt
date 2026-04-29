package com.sylvt3.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoritesText: TextView
    private lateinit var btnClearFavorites: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        favoritesText = findViewById(R.id.favoritesText)
        btnClearFavorites = findViewById(R.id.btnClearFavorites)
        btnBack = findViewById(R.id.btnBack)

        loadFavorites()

        btnClearFavorites.setOnClickListener {
            val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove("favoriteList")
            editor.apply()

            favoritesText.text = "No favorites saved"

            Toast.makeText(this, "Favorites deleted", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadFavorites() {
        val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
        val favorites = sharedPreferences.getString("favoriteList", "")

        if (favorites == "") {
            favoritesText.text = "No favorites saved"
        } else {
            favoritesText.text = favorites
        }
    }
}