package com.sylvt3.weatherapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    val weatherData = MutableLiveData<WeatherData>()
    val errorMessage = MutableLiveData<String>()

    private val queue: RequestQueue = Volley.newRequestQueue(application)

    fun fetchWeather(city: String) {
        val safeCity = city.replace(" ", "%20")
        val geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=$safeCity&count=1"

        val geoRequest = JsonObjectRequest(
            Request.Method.GET,
            geoUrl,
            null,
            { geoResponse ->
                val results = geoResponse.optJSONArray("results")

                if (results == null || results.length() == 0)
                {
                    errorMessage.value = "City not found"
                }

                else
                {
                    val location = results.getJSONObject(0)

                    val latitude = location.optDouble("latitude")
                    val longitude = location.optDouble("longitude")
                    val cityName = location.optString("name", city)

                    fetchWeatherByCoordinates(latitude, longitude, cityName)
                }
            },
            {
                errorMessage.value = "Location error"
            }
        )

        queue.add(geoRequest)
    }

    private fun fetchWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
        cityName: String
    ) {
        val weatherUrl =
            "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=$latitude" +
                    "&longitude=$longitude" +
                    "&current=temperature_2m,wind_speed_10m,weather_code" +
                    "&daily=temperature_2m_max,wind_speed_10m_max,weather_code" +
                    "&temperature_unit=fahrenheit" +
                    "&wind_speed_unit=mph" +
                    "&timezone=auto"

        val weatherRequest = JsonObjectRequest(
            Request.Method.GET,
            weatherUrl,
            null,
            { response ->
                val current = response.optJSONObject("current")
                val daily = response.optJSONObject("daily")

                if (current == null || daily == null)
                {
                    errorMessage.value = "Weather data not found"
                }

                else
                {
                    val currentTemp = current.optDouble("temperature_2m")
                    val currentWindSpeed = current.optDouble("wind_speed_10m")
                    val currentCode = current.optInt("weather_code")

                    val description = getWeatherDescription(currentCode)

                    val temps = daily.optJSONArray("temperature_2m_max")
                    val winds = daily.optJSONArray("wind_speed_10m_max")

                    if (temps == null || winds == null || temps.length() < 3 || winds.length() < 3)
                    {
                        errorMessage.value = "Forecast data not found"
                    }

                    else
                    {
                        val data = WeatherData(
                            cityName,
                            description,
                            "Temperature: $currentTemp°F",
                            "Wind: $currentWindSpeed mph",
                            "High: ${temps.optDouble(1)}°F",
                            "Wind: ${winds.optDouble(1)} mph",
                            "High: ${temps.optDouble(2)}°F",
                            "Wind: ${winds.optDouble(2)} mph"
                        )

                        weatherData.value = data
                    }
                }
            },
            {
                errorMessage.value = "Network error"
            }
        )

        queue.add(weatherRequest)
    }

    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Clear"
            1, 2 -> "Mostly clear"
            3 -> "Cloudy"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Drizzle"
            61, 63, 65 -> "Rain"
            71, 73, 75 -> "Snow"
            80, 81, 82 -> "Rain showers"
            95 -> "Thunderstorm"
            else -> "Unknown"
        }
    }
}