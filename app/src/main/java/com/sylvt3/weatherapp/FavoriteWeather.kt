package com.sylvt3.weatherapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val description: String,
    val temperature: String
)