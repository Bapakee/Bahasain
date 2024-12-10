package com.bahasain.ui.cultural.folklore

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.cultural.CulturalViewModel
import com.bahasain.ui.cultural.FolkloreAdapter
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityFolkloreBinding

class FolkloreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFolkloreBinding

    private lateinit var adapterFolklore: AllFolkloreAdapter

    private val viewModel: CulturalViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFolkloreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapterFolklore = AllFolkloreAdapter()

        binding.rvFolklore.layoutManager =  GridLayoutManager(this, 2)

        binding.rvFolklore.adapter = adapterFolklore

        getFolklore()
    }

    private fun getFolklore(){
        viewModel.getFolklore().observe(this){ result ->
            if (result != null ){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        adapterFolklore.submitList(result.data)
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }
}