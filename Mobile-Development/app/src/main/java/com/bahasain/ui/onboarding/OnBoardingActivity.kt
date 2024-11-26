package com.bahasain.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityOnBoardingBinding
import com.bahasain.ui.MainActivity
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.login.LoginActivity
import com.bahasain.ui.splash.SplashViewModel
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnBoardingAdapter

    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "OnBoardingPrefs"
    private val IS_ONBOARDING_COMPLETED = "isOnboardingCompleted"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val title1 = getString(R.string.title_onboarding_1)
        val desc1 = getString(R.string.txt_onboarding_1)
        val title2 = getString(R.string.title_onboarding_2)
        val desc2 = getString(R.string.txt_onboarding_2)
        val title3 = getString(R.string.title_onboarding_3)
        val desc3 = getString(R.string.txt_onboarding_3)


        val onboardingItems = listOf(
            OnBoardingItem(R.drawable.ob_image_1, title1, desc1),
            OnBoardingItem(R.drawable.ob_image_2, title2, desc2),
            OnBoardingItem(R.drawable.ob_image_3, title3, desc3)
        )

        viewPager = binding.viewPager
        adapter = OnBoardingAdapter(onboardingItems)
        viewPager.adapter = adapter

        val indicator = binding.indicator
        TabLayoutMediator(indicator, viewPager) { _, _ -> }.attach()

        binding.btnNext.setOnClickListener {
            if (viewPager.currentItem < onboardingItems.size - 1) {
                viewPager.currentItem += 1
            } else {
                saveOnBoardingPref()
                viewModel.getSession().observe(this) { user ->
                    if (user.token.isEmpty()) {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun saveOnBoardingPref() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_ONBOARDING_COMPLETED, true)
        editor.apply()
    }
}
