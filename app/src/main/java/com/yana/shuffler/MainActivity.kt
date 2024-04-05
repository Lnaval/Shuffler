package com.yana.shuffler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.yana.shuffler.databinding.ActivityMainBinding
import com.yana.shuffler.views.AddedBooksFragment
import com.yana.shuffler.views.SearchFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
//        val toolbar = binding.toolBar
//        setSupportActionBar(toolbar)

        val navView = binding.navView
        navView.setNavigationItemSelectedListener (this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchFragment()).commit()
            navView.setCheckedItem(R.id.navSearch)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navSearch -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchFragment()).commit()
            R.id.navBookList -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddedBooksFragment()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}