package com.example.antiwaste3_0.ui.dashboard

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.antiwaste3_0.*
import com.example.antiwaste3_0.databinding.FragmentDashboardBinding
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val btn1 : Button = root.findViewById<Button>(R.id.btn_mapEW)
        btn1.setOnClickListener{
            startActivity(Intent(activity, MapsActivity::class.java))        //used activity instead of "@LoginActivity"
        }

        val btn2 : Button = root.findViewById<Button>(R.id.btn_map2G)
        btn2.setOnClickListener{
            startActivity(Intent(activity, MapsActivity2::class.java))        //used activity instead of "@LoginActivity"
        }

        val btn3 : Button = root.findViewById<Button>(R.id.btn_UP)
        btn3.setOnClickListener{
            startActivity(Intent(activity, UploadPhotoActivity::class.java))        //used activity instead of "@LoginActivity"
        }

        val btn4 : Button = root.findViewById<Button>(R.id.btn_weather)
        btn4.setOnClickListener{
            startActivity(Intent(activity, WeatherActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    }

