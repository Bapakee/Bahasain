package com.bahasain.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bahasain.data.Result
import com.bahasain.ui.learn.LearnViewModel
import com.bahasain.ui.setting.SettingActivity
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<LearnViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){isGranted: Boolean ->
            if (isGranted){
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Notification permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33){
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        getScore()

        binding.swipeRefreshLayout.setOnRefreshListener {
            // Trigger the refresh logic here (e.g., reload data, getScore, etc.)
            getScore()  // Assuming getScore() will update the score

            // Stop the refresh animation once data is refreshed
            binding.swipeRefreshLayout.isRefreshing = false
        }


        binding.btnSetting.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getScore(){
        viewModel.getScore().observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.tvPoint.text = result.data?.point?.toString() ?: "0"
                        binding.tvStreak.text = result.data?.streak?.toString() ?: "0"
                    }
                    is Result.Error -> {

                    }
                }
            }

        }
    }
}