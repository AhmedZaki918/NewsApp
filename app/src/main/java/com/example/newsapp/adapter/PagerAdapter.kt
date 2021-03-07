package com.example.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.home.headlines.HeadlinesFragment
import com.example.newsapp.ui.home.business.BusinessFragment
import com.example.newsapp.ui.home.tech.TechFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HeadlinesFragment()
            }
            1 -> {
                BusinessFragment()
            }
            else -> {
                TechFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}