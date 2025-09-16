package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.donglab.screennameviewer.compose.R
import com.donglab.screennameviewer.compose.databinding.ActivityViewpagerSampleBinding

class ViewPagerSampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewpagerSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewpagerSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false // 스와이프 비활성화

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.nav_explore -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.nav_settings -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    private class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ViewPagerHomeFragment()
                1 -> ViewPagerExploreFragment()
                2 -> ViewPagerSettingsFragment()
                else -> ViewPagerHomeFragment()
            }
        }
    }
}