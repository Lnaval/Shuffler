package com.yana.shuffler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yana.shuffler.databinding.ActivityMainBinding
import com.yana.shuffler.views.AddedBooksFragment
import com.yana.shuffler.views.CalendarFragment
import com.yana.shuffler.views.HomeFragment
import com.yana.shuffler.views.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            replaceFragment(HomeFragment(), R.id.navHome)
        }
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> replaceFragment(HomeFragment(), R.id.navHome)
                R.id.navSearch -> replaceFragment(SearchFragment(), R.id.navSearch)
                R.id.navBookList -> replaceFragment(AddedBooksFragment(), R.id.navBookList)
                R.id.navCalendar -> replaceFragment(CalendarFragment(), R.id.navCalendar)
            }
            true
        }
    }

    fun replaceFragment(fragment: Fragment, selectedItemId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        binding.bottomNav.menu.findItem(selectedItemId).isChecked = true
    }
}