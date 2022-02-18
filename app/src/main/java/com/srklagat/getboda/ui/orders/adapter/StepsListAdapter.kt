package com.srklagat.getboda.ui.orders.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.srklagat.getboda.data.Steps
import com.srklagat.getboda.databinding.RowOrderLayoutBinding
import java.text.SimpleDateFormat
import java.util.*


class StepsListAdapter(private val orderOnClickListener: OrderOnClickListener) :
    ListAdapter<Steps, StepsListAdapter.StepsItemHolder>(
        StepsDiffer
    ) {
    inner class StepsItemHolder(private val binding: RowOrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            step: Steps,
            orderOnClickListener: OrderOnClickListener
        ) {


            step.apply {
                val typeString: String =
                    type?.substring(0, 1)?.toUpperCase() + type?.substring(1)?.toLowerCase()
                val SIMPLE_TIME_FORMAT = "hh:mm a"
                val sdf = SimpleDateFormat(SIMPLE_TIME_FORMAT)
                val date = Date(arrival!! * 1000)
                val arrivalTime = sdf.format(date)
                binding.tvTime.text = arrivalTime.toString()
                binding.tvLocation.text = locationName
                binding.textDelivery.text = typeString
                binding.tvStopsNum.text = stop.toString()

                val millis: Long = Date().time - step?.arrival!!
                //val hours = (millis / (1000 * 60 * 60)).toInt()
                val mins = (millis / (1000 * 60) % 60).toInt()

                binding.tvRemainingTime.text = String.format("%s mins", mins)
                binding.root.setOnClickListener {
                    orderOnClickListener.invoke(step, binding)
                }

            }
        }
    }

    companion object StepsDiffer : DiffUtil.ItemCallback<Steps>() {
        override fun areItemsTheSame(
            oldItem: Steps,
            newItem: Steps
        ): Boolean = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Steps,
            newItem: Steps
        ): Boolean = (oldItem == newItem)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsItemHolder {
        return StepsItemHolder(
            RowOrderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StepsItemHolder, position: Int) {
        holder.bind(getItem(position), orderOnClickListener)
    }
}

typealias OrderOnClickListener = (Steps, binding: RowOrderLayoutBinding) -> Unit


