package com.example.antiwaste3_0.model

import com.example.antiwaste3_0.controller.FirebaseAuthController
import com.example.antiwaste3_0.view.ui.home.HomeFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FirebaseAuthModel {
    val database = FirebaseFirestore.getInstance()

    val controller = FirebaseAuthController(HomeFragment())

    fun databaseCollection() {
        val user = Firebase.auth.currentUser
        var username: String
        var points: Int
        if (user != null) {
            database.collection("users").document(user.email.toString()).get()
                .addOnCompleteListener() { task ->
                    username = (task.result?.data?.getValue("username") as CharSequence?).toString()
                    points = task.result?.data?.getValue("rewardPts") as Int
                    controller.setPoints(points)
                    controller.setUsername(username)
                }


        }
    }

    fun logout(){

    }
}