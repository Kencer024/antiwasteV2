package com.example.antiwaste3_0.controller

import com.example.antiwaste3_0.model.RewardModel
import com.example.antiwaste3_0.view.ui.notifications.RewardFragment
import kotlinx.android.synthetic.main.fragment_home.*

class RewardController(private val view : RewardFragment) {
    private var points : Int = 0
    private var cost = 10

    private val model = RewardModel(this)

    fun Interface(){
        points = model.updatePoints(cost)
        view.tv_user_pts.text = points.toString()
    }
}