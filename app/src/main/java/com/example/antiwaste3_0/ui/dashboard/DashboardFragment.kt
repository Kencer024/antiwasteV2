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
import com.example.antiwaste3_0.MapsActivity
import com.example.antiwaste3_0.MapsActivity2
import com.example.antiwaste3_0.R
import com.example.antiwaste3_0.UploadPhotoActivity
import com.example.antiwaste3_0.databinding.FragmentDashboardBinding
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    val CITY: String = "singapore,sg"
    val API: String = "f9bc4c6a592a5913019746c8446edbf1"

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

        //for weather
        val text1 : TextView = root.findViewById<TextView>(R.id.tv_weather)
        onPostExecute(text1)

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun result(): String? {
        var response:String?
        try{
            response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                Charsets.UTF_8
            )
        }catch (e: Exception){
            response = null
        }
        return response
    }

    fun onPostExecute(text1 : TextView) {
        val result = result()
        try {
            /* Extracting JSON returns from the API */
            val jsonObj = JSONObject(result)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            //val wind = jsonObj.getJSONObject("wind")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
            val updatedAt:Long = jsonObj.getLong("dt")
            val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                Date(updatedAt*1000)
            )
            val weatherDescription = weather.getString("description")
            text1.text = weatherDescription.capitalize()
            //findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
            /*
            val temp = main.getString("temp")+"°C"
            val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
            val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
            val pressure = main.getString("pressure")
            val humidity = main.getString("humidity")
            val sunrise:Long = sys.getLong("sunrise")
            val sunset:Long = sys.getLong("sunset")
            val windSpeed = wind.getString("speed")

            val address = jsonObj.getString("name")+", "+sys.getString("country")
            /* Populating extracted data into our views */
            findViewById<TextView>(R.id.address).text = address
            findViewById<TextView>(R.id.updated_at).text =  updatedAtText

            findViewById<TextView>(R.id.temp).text = temp
            findViewById<TextView>(R.id.temp_min).text = tempMin
            findViewById<TextView>(R.id.temp_max).text = tempMax
            findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                Date(sunrise*1000)
            )
            findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                Date(sunset*1000)
            )
            findViewById<TextView>(R.id.wind).text = windSpeed
            findViewById<TextView>(R.id.pressure).text = pressure
            findViewById<TextView>(R.id.humidity).text = humidity
            /* Views populated, Hiding the loader, Showing the main design */
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE*/
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                "no weather data",
                Toast.LENGTH_SHORT
            ).show()
        }

        }
    }

