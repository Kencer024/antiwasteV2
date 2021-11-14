package com.example.antiwaste3_0.view.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.*
import com.example.antiwaste3_0.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class RewardFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

        tv_greendot.setOnClickListener{
            updatePoints(10)
        }

        tv_busybowl.setOnClickListener{
            updatePoints(10)
        }

        tv_veganbburg.setOnClickListener{
            updatePoints(10)
        }

        tv_pastaexpress.setOnClickListener{
            updatePoints(10)
        }


        })
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updatePoints(points_req : Int) {
        val database = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser
        lateinit var points : Number
        if (user != null) {
            database.collection("users").document(user.email.toString()).get()
                .addOnSuccessListener() { task ->
                    points = task.data?.getValue("rewardPts") as Number         //getPoints
                    if(points.toInt() >= points_req)     //checks the points
                    {
                        database.collection("users")                        //updatePoints-2
                            .document(user.email.toString())
                            .update("rewardPts", points.toInt()-points_req)
                        Toast.makeText(
                            activity,
                            "Reward Redeemed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        Toast.makeText(
                            activity,
                            "Insufficient points",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }

}

