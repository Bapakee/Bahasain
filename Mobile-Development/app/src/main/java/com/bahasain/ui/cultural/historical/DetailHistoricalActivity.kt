package com.bahasain.ui.cultural.historical

import android.annotation.SuppressLint
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
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityDetailHistoricalBinding

class DetailHistoricalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoricalBinding

    private lateinit var historicalId: String

    private val viewModel by viewModels<CulturalViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailHistoricalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        historicalId =  intent.getStringExtra("HISTORICAL_ID") ?: ""
        if (historicalId.isNotEmpty()){
            viewModel.setHistoricalId(historicalId)
            getDetailHistorical(historicalId)
        }

        getDetailHistorical(historicalId)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun getDetailHistorical(historicalId: String){
        viewModel.getDetailHistorical(historicalId).observe(this){ result ->
            if (result != null) {
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
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