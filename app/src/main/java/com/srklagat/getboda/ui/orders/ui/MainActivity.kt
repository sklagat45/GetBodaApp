package com.srklagat.getboda.ui.orders.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.srklagat.getboda.R
import com.srklagat.getboda.databinding.ActivityMainBinding
import com.srklagat.getboda.ui.orders.adapter.BottomBarAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerAdapter: BottomBarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()

    }


    private fun setUpViews() {
        //optimisation
        binding.viewPager.offscreenPageLimit = 3
        pagerAdapter = BottomBarAdapter(supportFragmentManager)
        pagerAdapter.addFragments(OrdersFragment())
        pagerAdapter.addFragments(MapsFragment())
        pagerAdapter.addFragments(PlaceholderFragment())
        binding.viewPager.adapter = pagerAdapter
        bottomNavBar()
    }


    private fun bottomNavBar() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            // uncheck the other items.
            when (item.itemId) {
                R.id.m_home -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.m_steps -> {
                    binding.viewPager.currentItem = 1
                }
                R.id.m_setting -> {
                    binding.viewPager.currentItem = 2
                }
            }
            true
        }

    }


}