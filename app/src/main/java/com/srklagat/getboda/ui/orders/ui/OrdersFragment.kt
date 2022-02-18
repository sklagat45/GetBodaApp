package com.srklagat.getboda.ui.orders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.srklagat.getboda.databinding.FragmentOrdersBinding
import com.srklagat.getboda.databinding.FragmentUpcomingOrdersBinding
import com.srklagat.getboda.ui.orders.adapter.OrderTabsPagerAdapter

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersBinding.bind(view)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = OrderTabsPagerAdapter(fm = childFragmentManager)
        adapter.addFragment(UpcomingOrdersFragment(), "To do")
        adapter.addFragment(PastOrdersFragment(), "Completed")
        binding.viewPager.adapter = adapter
        binding.tabsLayout.setupWithViewPager(binding.viewPager)
    }


}