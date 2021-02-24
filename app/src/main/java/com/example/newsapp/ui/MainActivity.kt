package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.wishlist.WishlistFragment
import com.example.newsapp.ui.home.HomeFragment
import com.example.newsapp.ui.settings.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val favouriteFragment = WishlistFragment()
        val settingFragment = SettingFragment()
        // View default fragment
        createFragment(homeFragment)

        // Click listener on bottom navigation
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> createFragment(homeFragment)
                R.id.ic_favourite -> createFragment(favouriteFragment)
                R.id.ic_setting -> createFragment(settingFragment)
            }
            true
        }
    }

    /**
     * Replace fragments
     */
    private fun createFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
}