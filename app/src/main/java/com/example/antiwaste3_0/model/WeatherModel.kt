package com.example.antiwaste3_0.model

import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.antiwaste3_0.R
import com.example.antiwaste3_0.WeatherActivity
import com.example.antiwaste3_0.controller.WeatherController
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class WeatherModel : AppCompatActivity() {

    var controller = WeatherController(WeatherActivity())

    val CITY: String = "singapore,sg"
    val API: String = "f9bc4c6a592a5913019746c8446edbf1"

    fun onPreExecute() {
        /* Showing the ProgressBar, Making the main design GONE */
        findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
        findViewById<TextView>(R.id.errorText).visibility = View.GONE
    }
    fun doInBackground(vararg params: String?): String? {
        var response:String?
        try{
            response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                Charsets.UTF_8
            )
        }catch (e: Exception){
            response = null
        }
        return response
    }
    fun onPostExecute(result: String?) {
        try {
            /* Extracting JSON returns from the API */
            val jsonObj = JSONObject(result)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val wind = jsonObj.getJSONObject("wind")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
            val updatedAt: Long = jsonObj.getLong("dt")
            val updatedAtText =
                "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt * 1000)
                )
            val temp = main.getString("temp") + "°C"
            val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
            val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
            val pressure = main.getString("pressure")
            val humidity = main.getString("humidity")
            val sunrise: Long = sys.getLong("sunrise")
            val sunset: Long = sys.getLong("sunset")
            val windSpeed = wind.getString("speed")
            val weatherDescription = weather.getString("description")
            val address = jsonObj.getString("name") + ", " + sys.getString("country")
        } catch (e: Exception) {
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
        }
    }
}