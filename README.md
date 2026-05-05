Welcome to the Weather App

This app will allow a user to enter a location, preferably a city/town location, and retrieve that location's current weather with details including temperature, weather description, wind speed, and the following days' forecast. The user will enter a location in the search bar with the text hint saying 'Enter Location', then press the green search button. A loading text will appear that will act as a way for the user to know that what they entered is being processed. If a failure occurs, then the app will signal this by using one of the text fields to say try again, and if successful, the user will be able to view that location's weather. Once the user has the data, they can press the green heart button in the top right corner to save to favorites, and then also be able to go to that favorites list by using the top left button. Once in the favorite menu, the user can view the very basic data of that location and remove it.

If the app is not working, here is an APK of the app, and if that also does not work, then here is a YouTube video of the app being demonstrated:

APK: https://drive.google.com/drive/folders/1Oi0WN5m_AQdO-gCmxpa82s0c3WWS52KT?usp=sharing

YouTube: https://youtu.be/Jn1nAQrz5BA

How the app works:

This app takes the text entered in the search bar and formats it so the first API, https://open-meteo.com/en/docs/geocoding-api. This API takes a location name and gets the geo location data, including latitude, longitude, area codes, elevation, etc. This is important for two things: one, the second API uses lat and longitude to retrieve weather data, and two, it is very accurate. The next API, https://open-meteo.com/. This API is very powerful and will return accurate weather data for a precise location. So the first API will feed the next API, which is then displayed to the views. The nice thing about these APIs is that they both come from the same place, Open-Metro, which has fantastic documentation and is free, which you can't beat that price.
