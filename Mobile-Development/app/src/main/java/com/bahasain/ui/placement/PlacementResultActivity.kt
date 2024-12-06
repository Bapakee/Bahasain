package com.bahasain.ui.placement

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.ui.MainActivity
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityPlacementResultBinding

class PlacementResultActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityPlacementResultBinding

    private var level: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementResultBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        level = intent.getIntExtra("LEVEL", 0)

        val resultLevel = when(level){
            1 -> "Basic"
            2 -> "Intermediate"
            3 -> "Advance"
            else -> "Kosong"
        }

        binding.titleDescResult.text = getString(R.string.title_desc_result_placement, resultLevel)
        binding.descResult.text = getString(R.string.desc_result_placement, resultLevel)

        binding.btnStart.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}