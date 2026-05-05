package com.sylvt3.weatherapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(favorite: FavoriteWeather)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteWeather>

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteWeather)
}