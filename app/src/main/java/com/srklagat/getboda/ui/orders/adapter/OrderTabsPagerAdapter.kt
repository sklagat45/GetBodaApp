package com.srklagat.getboda.ui.orders.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * BidsTabsPagerAdapter
 */
internal class OrderTabsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val titles: ArrayList<String> = ArrayList()
    private val fragmentList: ArrayList<Fragment> = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    /**
     * Add Fragment
     * @param fragment
     * @param title
     */
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titles.add(title)
    }
}