package com.example.antiwaste3_0.controller


import com.example.antiwaste3_0.model.FirebaseAuthModel
import com.example.antiwaste3_0.view.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_home.*

class FirebaseAuthController(private val home: HomeFragment) {
    lateinit var username: String
    var points: Int = 0

    private val model = FirebaseAuthModel()

    @JvmName("setUsername1")
    fun setUsername(username:String){
        this.username = username
    }

    @JvmName("setPoints1")
    fun setPoints(points:Int){
        this.points = points
    }


    fun firebaseInterface(){
        home.btn_logout.setOnClickListener{
            model.logout()

        home.tv_user_pts.text = username
        }

    }

}