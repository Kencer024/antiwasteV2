package com.example.antiwaste3_0.model

import android.content.Context
import com.example.antiwaste3_0.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer

class MapModel2 {
    private lateinit var mMap: GoogleMap


    @JvmName("getLayer1")
    fun getLayer() : KmlLayer{
        return KmlLayer(mMap, R.raw.raw_map2gcp, this as Context)
    }


    fun moveCamera() : LatLng{
        return LatLng(1.35,103.8)
    }

    val sg = LatLng(1.35,103.8)



}