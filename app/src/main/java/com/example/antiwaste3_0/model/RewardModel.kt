package com.example.antiwaste3_0.model

import android.widget.Toast
import com.example.antiwaste3_0.controller.RewardController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RewardModel(private val controller : RewardController) {
    fun updatePoints(points_req : Int) : Int {
        val database = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser
        var points : Int = 0
        if (user != null) {
            database.collection("users").document(user.email.toString()).get()
                .addOnSuccessListener() { task ->
                    points = task.data?.getValue("rewardPts") as Int        //getPoints
                    if(points.toInt() >= points_req)     //checks the points
                    {
                        database.collection("users")                 //updatePoints-cost
                            .document(user.email.toString())
                            .update("rewardPts", points.toInt()-points_req)
                    }
                    else{
                        //if less points
                    }
                }

        }
        return points
    }
}