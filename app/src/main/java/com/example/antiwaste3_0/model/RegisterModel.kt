package com.example.antiwaste3_0.model

import android.app.Activity
import android.widget.Toast
import com.example.antiwaste3_0.controller.RegisterController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterModel(private val controller : RegisterController) {
    fun databaseCollection(email : String, password : String) {
        //register using firebaseauth
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

    fun createFirestoreDatabase(email : String, phoneNo : Int, username : String, ){
        val database = FirebaseFirestore.getInstance()
        val user:MutableMap<String, Any>  = HashMap()
        user["email"] = email
        user["phoneNumber"] = phoneNo
        user["username"] = username
        user["rewardPts"] = 0

        database.collection("users").document(email).set(user)
    }
}