package com.example.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.home.tabs.headlines.HeadlinesFragment
import com.example.newsapp.ui.home.tabs.business.BusinessFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return HeadlinesFragment()
        }
        return BusinessFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}