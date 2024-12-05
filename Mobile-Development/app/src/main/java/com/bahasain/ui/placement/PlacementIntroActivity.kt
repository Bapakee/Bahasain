package com.bahasain.ui.placement

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.data.Result
import com.bahasain.ui.MainActivity
import com.bahasain.ui.ViewModelFactory
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityPlacementBinding
import com.dicoding.bahasain.databinding.ActivityPlacementIntroBinding

class PlacementIntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementIntroBinding

    private val viewModel by viewModels<PlacementViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlacementIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnStart.setOnClickListener{
            val intent = Intent(this, PlacementActivity::class.java)
            startActivity(intent)
        }

        binding.btnSetLevel.setOnClickListener{
            viewModel.getSession().observe(this) { session ->
                if (session != null) {
                    setLevel()
                    val updatedSession = session.copy(userLevel = 1)
                    viewModel.saveSession(updatedSession)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Gagal memuat sesi", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setLevel() {
        viewModel.setLevel(BASIC_LEVEL).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                    }

                    is Result.Success -> {
                        Toast.makeText(this, "level anda basic", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                    }
                }
            }
        }
    }

    companion object {
        private const val BASIC_LEVEL = 1
    }
}