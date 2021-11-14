package com.example.antiwaste3_0.controller

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.antiwaste3_0.model.UploadPhotoModel
import com.example.antiwaste3_0.UploadPhotoActivity
import kotlinx.android.synthetic.main.activity_upload_photo.*

class UploadPhotoController(private val uploadPhotoActivity: UploadPhotoActivity) {

    private var photo_uri : Uri? = null
    private val model = UploadPhotoModel(this)

    fun getAct() : AppCompatActivity{
        return this.uploadPhotoActivity
    }

    fun selectImage(){
        model.select_image()
    }

    fun uploadImage(){
        model.upload_image()
        photo_uri = model.getUri()
        this.uploadPhotoActivity.image_view.setImageURI(photo_uri)
    }


}