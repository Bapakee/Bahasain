package com.bahasain.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bahasain.data.Result
import com.bahasain.ui.learn.LearnViewModel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<LearnViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getScore()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
    }

    private fun getScore(){
        viewModel.getScore().observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.tvPoint.text = result.data?.point.toString()
                        binding.tvStreak.text = result.data?.streak.toString()
                    }
                    is Result.Error -> {

                    }
                }
            }

        }
    }
}