package com.sylvt3.weatherapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var rootLayout: ConstraintLayout
    private lateinit var getCity: EditText
    private lateinit var btnSearch: Button

    private lateinit var currentState: TextView
    private lateinit var currentDescription: TextView
    private lateinit var currentTemperature: TextView
    private lateinit var currentWind: TextView

    private lateinit var day1: TextView
    private lateinit var day1Temperature: TextView
    private lateinit var day1Wind: TextView

    private lateinit var day2: TextView
    private lateinit var day2Temperature: TextView
    private lateinit var day2Wind: TextView

    private lateinit var btnSaveFavorite: Button

    private lateinit var btnViewFavorites: Button

    private lateinit var weatherViewModel: WeatherViewModel

    private var savedCity = ""
    private var savedDescription = ""
    private var savedTemperature = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.rootLayout)
        getCity = findViewById(R.id.getCity)
        btnSearch = findViewById(R.id.btnSearch)

        currentState = findViewById(R.id.CurrentState)
        currentDescription = findViewById(R.id.CurrentDescription)
        currentTemperature = findViewById(R.id.CurrentTemperature)
        currentWind = findViewById(R.id.CurrentWind)

        day1 = findViewById(R.id.day1)
        day1Temperature = findViewById(R.id.day1Temperature)
        day1Wind = findViewById(R.id.day1Wind)

        day2 = findViewById(R.id.day2)
        day2Temperature = findViewById(R.id.day2Temperature)
        day2Wind = findViewById(R.id.day2Wind)

        btnSaveFavorite = findViewById(R.id.btnSaveFavorite)
        btnViewFavorites = findViewById(R.id.btnViewFavorites)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        weatherViewModel.weatherData.observe(this) { data ->
            currentState.text = "${data.cityName}'s Forecast"
            currentDescription.text = data.description
            currentTemperature.text = data.currentTemperature
            currentWind.text = data.currentWind

            day1Temperature.text = data.day1Temperature
            day1Wind.text = data.day1Wind

            day2Temperature.text = data.day2Temperature
            day2Wind.text = data.day2Wind

            savedCity = data.cityName
            savedDescription = data.description
            savedTemperature = data.currentTemperature

            updateBackground(data.description)
        }

        weatherViewModel.errorMessage.observe(this) { message ->
            clearFields()
            currentState.text = message
            currentDescription.text = "Please try again"
            rootLayout.setBackgroundColor(getColor(R.color.white))
        }

        btnSearch.setOnClickListener{
            val city = getCity.text.toString().trim()

            if (city.isEmpty()) {
                clearFields()
                currentState.text = "Enter a city"
            }

            else
            {
                clearFields()
                currentState.text = "Loading..."
                weatherViewModel.fetchWeather(city)
            }
        }

        btnSaveFavorite.setOnClickListener{
            if (savedCity.isEmpty())
            {
                Toast.makeText(this, "Search for weather first", Toast.LENGTH_SHORT).show()
            }

            else
            {
                saveFavorite(savedCity, savedDescription, savedTemperature)
                Toast.makeText(this, "Saved to favorites", Toast.LENGTH_SHORT).show()
            }
        }

        btnViewFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateBackground(description: String)
    {
        val desc = description.lowercase()

        val color = when {
            desc.contains("sun") || desc.contains("clear") -> getColor(R.color.merigold)
            desc.contains("cloud") -> getColor(R.color.cloud)
            desc.contains("rain") -> getColor(R.color.cloudy_blue)
            else -> getColor(R.color.white)
        }

        rootLayout.setBackgroundColor(color)
    }

    private fun clearFields()
    {
        currentDescription.text = ""
        currentTemperature.text = ""
        currentWind.text = ""

        day1Temperature.text = ""
        day1Wind.text = ""

        day2Temperature.text = ""
        day2Wind.text = ""
    }

    private fun saveFavorite(city: String, description: String, temperature: String)
    {
        val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)

        val oldFavorites = sharedPreferences.getString("favoriteList", "")

        val newFavorite = "$city - $description - $temperature"

        val updatedFavorites = if (oldFavorites == "")
        {
            newFavorite
        }

        else
        {
            oldFavorites + "\n" + newFavorite
        }

        val editor = sharedPreferences.edit()
        editor.putString("favoriteList", updatedFavorites)
        editor.apply()
    }
}