package com.example.antiwaste3_0.controller

import android.content.Intent
import com.example.antiwaste3_0.LoginActivity
import com.example.antiwaste3_0.MainActivity
import com.example.antiwaste3_0.model.LoginModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginController(private val view : LoginActivity) {
    private lateinit var email : String
    private lateinit var password : String

    val model = LoginModel(this)

    fun Interface(){
        email = view.et_login_email.toString()
        password = view.et_login_password.toString()
        model.databaseCollection(email,password)
    }

    /**
     * Send user to main screen with user id and email
     */
    fun intent(){
        val intent =
            Intent(view, MainActivity::class.java)
    }

}