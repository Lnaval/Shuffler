package com.yana.shuffler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.yana.shuffler.databinding.ActivityMainBinding
import com.yana.shuffler.views.AddedBooksFragment
import com.yana.shuffler.views.CalendarFragment
import com.yana.shuffler.views.HomeFragment
import com.yana.shuffler.views.SearchFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView = binding.navView
        navView.setNavigationItemSelectedListener (this)

        drawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        toggle.syncState()
        drawerLayout.addDrawerListener(toggle)

        if(savedInstanceState == null){
            replaceFragment(HomeFragment(), R.id.navHome)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navHome -> replaceFragment(HomeFragment(), R.id.navHome)
            R.id.navSearch -> replaceFragment(SearchFragment(), R.id.navSearch)
            R.id.navBookList -> replaceFragment(AddedBooksFragment(), R.id.navBookList)
            R.id.navCalendar -> replaceFragment(CalendarFragment(), R.id.navCalendar)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun replaceFragment(fragment: Fragment, item: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
        navView.setCheckedItem(item)
    }
}