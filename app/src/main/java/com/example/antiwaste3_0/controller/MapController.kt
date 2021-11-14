package com.example.antiwaste3_0.controller

import com.example.antiwaste3_0.MapsActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.example.antiwaste3_0.model.MapModel

class MapController(private val mapActivity: MapsActivity) {
    private lateinit var mMap: GoogleMap
    private var model = MapModel()

    fun addLayer(){
        model.getLayer().addLayerToMap()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            model.moveCamera(),12f))

    }

}