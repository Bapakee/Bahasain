package com.bahasain.ui.cultural.recipe

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.cultural.CulturalViewModel
import com.bumptech.glide.Glide
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityDetailRecipeBinding

class DetailRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipeBinding

    private lateinit var recipeId: String

    private val viewModel by viewModels<CulturalViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recipeId = intent.getStringExtra("RECIPE_ID") ?: ""
        if (recipeId.isNotEmpty()){
            viewModel.setRecipeId(recipeId)
            getDetailRecipe(recipeId)
        }

        getDetailRecipe(recipeId)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun getDetailRecipe(recipeId: String){
        viewModel.getDetailRecipe(recipeId).observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {

                        Glide.with(this)
                            .load(result.data?.data?.imageUrl)
                            .into(binding.ivRecipe)

                        binding.btnPlay.setOnClickListener{
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(result.data?.data?.videoUrl)
                            }
                            startActivity(intent)
                        }

                        val htmlContent = result.data?.data?.content
                        binding.webView.apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            webViewClient = object : WebViewClient() {}
                            if (htmlContent != null) {
                                loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
                            }
                        }
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }
}