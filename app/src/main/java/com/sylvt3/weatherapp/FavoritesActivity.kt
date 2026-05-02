package com.sylvt3.weatherapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FavoritesActivity : AppCompatActivity() {

    private lateinit var database: WeatherDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        database = WeatherDatabase.getDatabase(this)

        btnBack = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.favoritesRecyclerView)

        adapter = FavoriteAdapter(emptyList()) { favorite ->
            deleteFavorite(favorite)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnBack.setOnClickListener {
            finish()
        }

        loadFavorites()
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            val favorites = database.favoriteDao().getAllFavorites()
            adapter.updateFavorites(favorites)
        }
    }

    private fun deleteFavorite(favorite: FavoriteWeather) {
        lifecycleScope.launch {
            database.favoriteDao().deleteFavorite(favorite)
            loadFavorites()
        }
    }
}