package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.R
import com.donglab.screennameviewer.compose.databinding.ActivityShowhideFragmentBinding

class ShowHideFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowhideFragmentBinding

    private val fragmentList = mutableListOf<Fragment>()
    private var currentFragmentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowhideFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragments()
        setupBottomNavigation()

        if (savedInstanceState == null) {
            showFragment(0)
        }
    }

    private fun initFragments() {
        val homeFragment = ShowHideHomeFragment()
        val searchFragment = ShowHideSearchFragment()
        val profileFragment = ShowHideProfileFragment()

        fragmentList.addAll(listOf(homeFragment, searchFragment, profileFragment))

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, homeFragment, "home")
            add(R.id.fragmentContainer, searchFragment, "search")
            add(R.id.fragmentContainer, profileFragment, "profile")

            hide(searchFragment)
            hide(profileFragment)
        }.commit()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showFragment(0)
                    true
                }
                R.id.nav_search -> {
                    showFragment(1)
                    true
                }
                R.id.nav_profile -> {
                    showFragment(2)
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(index: Int) {
        if (index == currentFragmentIndex || index >= fragmentList.size) return

        val transaction = supportFragmentManager.beginTransaction()

        val previousFragment = fragmentList[currentFragmentIndex]
        val targetFragment = fragmentList[index]

        previousFragment.onPause()

        transaction.hide(previousFragment)
        transaction.show(targetFragment)
        transaction.commit()

        targetFragment.onResume()

        currentFragmentIndex = index
    }
}