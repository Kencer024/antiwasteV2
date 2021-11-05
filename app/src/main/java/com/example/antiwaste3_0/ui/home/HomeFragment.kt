package com.example.antiwaste3_0.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.antiwaste3_0.LoginActivity
import com.example.antiwaste3_0.MapsActivity
import com.example.antiwaste3_0.R
import com.example.antiwaste3_0.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    val CITY: String = "singapore,sg"
    val API: String = "f9bc4c6a592a5913019746c8446edbf1"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val database = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        //logout button
        val btn_log_out : Button = root.findViewById<Button>(R.id.btn_logout)
        btn_log_out.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))        //used activity instead of "@LoginActivity"
        }


        //once user signs in, display username and reward points
        val user = Firebase.auth.currentUser
        val text1 : TextView = root.findViewById<TextView>(R.id.tv2_user_id)
        val text2 : TextView = root.findViewById<TextView>(R.id.tv_user_pts)
        if (user != null) {
            database.collection("users").document(user.email.toString()).get()
                .addOnCompleteListener(){task ->
                    text1.text = task.result?.data?.getValue("username") as CharSequence?
                    val points = task.result?.data?.getValue("rewardPts")
                    text2.text = "$points points"
                }

            //
            //print(user.email)
        } else {
            // No user is signed in
        }
        // [END check_current_user]


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}