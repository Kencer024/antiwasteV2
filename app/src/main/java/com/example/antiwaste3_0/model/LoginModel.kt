package com.example.antiwaste3_0.model

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.antiwaste3_0.MainActivity
import com.example.antiwaste3_0.controller.LoginController
import com.google.firebase.auth.FirebaseAuth

class LoginModel(private val controller : LoginController) {
    fun databaseCollection(email : String, password : String) {
        //Log-in using firebaseauth
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(
                    this as Activity,
                    "Log in successful",
                    Toast.LENGTH_SHORT
                ).show()
                controller.intent()
            }   else {
                //if login not successful then show error message
                Toast.makeText(
                    this as Activity,
                    task.exception!!.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}