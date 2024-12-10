package com.bahasain.ui.cultural.recipe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.cultural.CulturalViewModel
import com.bahasain.ui.cultural.RecipeAdapter
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    private lateinit var adapterRecipe: AllRecipeAdapter

    private val viewModel by viewModels<CulturalViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapterRecipe = AllRecipeAdapter()

        binding.rvRecipe.layoutManager = GridLayoutManager(this, 3)
        binding.rvRecipe.adapter = adapterRecipe

        getRecipe()
    }

    private fun getRecipe() {
        viewModel.getRecipe().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        adapterRecipe.submitList(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }
}