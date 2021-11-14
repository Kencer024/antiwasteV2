package com.example.antiwaste3_0.controller

import com.example.antiwaste3_0.WeatherActivity
import com.example.antiwaste3_0.model.WeatherModel

class WeatherController(private val weatherActivity: WeatherActivity) {
    var model = WeatherModel()

    fun weatherInterface(){
        model.onPreExecute()
        var params = model.doInBackground()
        model.onPostExecute(params)
    }
}