package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "홈"
                1 -> "탐색"
                2 -> "설정"
                else -> "탭 ${position + 1}"
            }
        }.attach()
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