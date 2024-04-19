package com.yana.shuffler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yana.shuffler.databinding.ActivityAuthBinding

private const val SP_STRING = "sharedPrefs"
private const val AUTH_KEY = "name"
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        val sharedPreferences = getSharedPreferences(SP_STRING, MODE_PRIVATE)
        val check = sharedPreferences.getString(AUTH_KEY, "")
        if(check!="false"){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerAuth, fragment)
            .commit()
    }
}


