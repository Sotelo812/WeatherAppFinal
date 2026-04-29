package com.sylvt3.weatherapp

data class WeatherData(
    val cityName: String,
    val description: String,
    val currentTemperature: String,
    val currentWind: String,
    val day1Temperature: String,
    val day1Wind: String,
    val day2Temperature: String,
    val day2Wind: String
)