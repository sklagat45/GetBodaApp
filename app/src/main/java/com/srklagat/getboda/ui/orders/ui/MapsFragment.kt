package com.srklagat.getboda.ui.orders.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.srklagat.getboda.R
import com.srklagat.getboda.data.Steps
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val markers = getOrderSteps()

        markers.forEach { step ->
            val marker = LatLng(step.lat, step.long)
            googleMap.addMarker(
                MarkerOptions().position(marker).title(step.locationName)
            )
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 12f), 5000, null);
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    private fun getOrderSteps(): List<Steps> {
        val stepsList: ArrayList<Steps> = ArrayList()
        try {
            val obj = JSONObject(getJsonDataFromSteps()!!)
            val objDetails = obj.getJSONObject("details")
            val stepsArray = objDetails.getJSONArray("steps")
            var stop: Int = 0
            for (i in 1 until stepsArray.length()) {
                stop += 1
                val step = stepsArray.getJSONObject(i)
                val id = step.getInt("distance")
                val arrival = step.getLong("arrival")
                val type = step.getString("type")

                val locationArray = step.getJSONArray("location")
                val latitude = locationArray?.get(1)
                val longitude = locationArray.get(0)

                val locationName = "145 Kilimani, Nairobi, Kenya"

                val stepDetails =
                    Steps(
                        id,
                        arrival,
                        longitude as Double,
                        latitude as Double,
                        stop,
                        locationName,
                        type
                    )
                stepsList.add(stepDetails)
            }
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }

        return stepsList
    }

    private fun getJsonDataFromSteps(): String? {
        val jsonString: String
        try {
            jsonString =
                requireContext().assets.open("route.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}