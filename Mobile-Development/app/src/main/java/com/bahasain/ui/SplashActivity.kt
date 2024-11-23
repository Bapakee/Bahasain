package com.bahasain.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.ui.main.MainActivity
import com.dicoding.bahasain.R
import com.bahasain.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "OnBoardingPrefs"
    private val IS_ONBOARDING_COMPLETED = "isOnboardingCompleted"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isOnboardingCompleted()){
                navigateToOnBoardingActivity()
            }else{
                navigateToMainActivity()
            }
        }, 2000)

    }

    private fun navigateToOnBoardingActivity() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(IS_ONBOARDING_COMPLETED, false)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}