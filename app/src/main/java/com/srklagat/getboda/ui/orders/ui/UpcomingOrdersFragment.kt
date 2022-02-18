package com.srklagat.getboda.ui.orders.ui

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srklagat.getboda.data.Steps
import com.srklagat.getboda.databinding.FragmentUpcomingOrdersBinding
import com.srklagat.getboda.ui.orders.adapter.StepsListAdapter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UpcomingOrdersFragment : Fragment() {
    private var binding: FragmentUpcomingOrdersBinding? = null
    private lateinit var stepsListAdapter: StepsListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpcomingOrdersBinding.bind(view)
        setUpRv()
        setViews()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingOrdersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun setViews() {
        val step = getNextOrderSteps()[0]
        step.apply {
            val typeString: String =
                type?.substring(0, 1)?.toUpperCase() + type?.substring(1)?.toLowerCase()
            val SIMPLE_TIME_FORMAT = "hh:mm a"
            val sdf = SimpleDateFormat(SIMPLE_TIME_FORMAT)
            val date = Date(arrival!! * 1000)
            val arrivalTime = sdf.format(date)
            binding?.tvTime?.text = arrivalTime.toString()
            binding?.tvLocation?.text = locationName
            binding?.textDelivery?.text = typeString
            binding?.tvStopsNum?.text = stop.toString()

            val millis: Long = Date().time - step?.arrival!!
            //val hours = (millis / (1000 * 60 * 60)).toInt()
            val mins = (millis / (1000 * 60) % 60).toInt()

            binding?.tvStopDuration?.text = String.format("%s mins", mins)
        }

    }

    private fun setUpRv() {
        setRecyclerViewNoDivider(binding?.rvScheduledOrders)
        stepsListAdapter = StepsListAdapter { step, binding ->

        }
        binding?.rvScheduledOrders?.adapter = stepsListAdapter

        stepsListAdapter.submitList(getOrderSteps())
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpcomingOrdersFragment()
    }

    private fun setRecyclerViewNoDivider(recyclerView: RecyclerView?) {
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }


    private fun getOrderSteps(): List<Steps> {
        val stepsList: ArrayList<Steps> = ArrayList()
        try {
            val obj = JSONObject(getJsonDataFromSteps()!!)
            val objDetails = obj.getJSONObject("details")
            val stepsArray = objDetails.getJSONArray("steps")
            var stop: Int = 1
            for (i in 1 until stepsArray.length()) {

                stop += 1
                val step = stepsArray.getJSONObject(i)
                val id = step.getInt("distance")
                val arrival = step.getLong("arrival")
                val type = step.getString("type")

                val locationArray = step.getJSONArray("location")
                val latitude = locationArray?.get(0)
                val longitude = locationArray.get(1)

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

    private fun getNextOrderSteps(): List<Steps> {
        val stepsList: ArrayList<Steps> = ArrayList()
        try {
            val obj = JSONObject(getJsonDataFromSteps()!!)
            val objDetails = obj.getJSONObject("details")
            val stepsArray = objDetails.getJSONArray("steps")
            var stop: Int = 0
            for (i in 0 until 1) {
                stop += 1
                val step = stepsArray.getJSONObject(i)
                val id = step.getInt("distance")
                val arrival = step.getLong("arrival")
                val type = step.getString("type")

                val locationArray = step.getJSONArray("location")
                val latitude = locationArray?.get(0)
                val longitude = locationArray.get(1)

                val locationName = "1634 Afia Center, Nairobi, Kenya"

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

