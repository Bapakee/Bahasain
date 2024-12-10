package com.bahasain.ui.cultural.historical

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.cultural.CulturalViewModel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityHistoricalBinding

class HistoricalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricalBinding

    private lateinit var adapterHistorical: AllHistoricalAdapter

    private val viewModel by viewModels<CulturalViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoricalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapterHistorical = AllHistoricalAdapter()

        binding.rvHistorical.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvHistorical.adapter = adapterHistorical

        getHistorical()
    }

    private fun getHistorical(){
        viewModel.getHistorical().observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        adapterHistorical.submitList(result.data)
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }
}