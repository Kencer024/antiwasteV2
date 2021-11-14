package com.example.antiwaste3_0.controller

import android.content.Intent

import com.example.antiwaste3_0.MainActivity
import com.example.antiwaste3_0.RegisterActivity
import com.example.antiwaste3_0.model.RegisterModel
import kotlinx.android.synthetic.main.activity_login.*

class RegisterController(private val view : RegisterActivity) {
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var username : String
    private var phoneNo : Int = 0

    val model = RegisterModel(this)

    fun Interface(){
        email = view.et_login_email.toString()
        password = view.et_login_password.toString()
        model.databaseCollection(email,password)
        model.createFirestoreDatabase(email,phoneNo,username)
    }

    /**
     * Send user to main screen with user id and email
     */
    fun intent(){
        val intent =
            Intent(view, MainActivity::class.java)

    }
}