package com.bahasain.ui.vocab.category

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.vocab.VocabViewModel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityWordDetailBinding

class WordDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordDetailBinding

    private lateinit var wordId: String

    private val viewModel by viewModels<VocabViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        wordId = intent.getStringExtra("WORD_ID") ?: ""
        if (wordId.isNotEmpty()){
            viewModel.setWOrdId(wordId)
            getDetailWord(wordId)
        }

        getDetailWord(wordId)
    }

    private fun getDetailWord(wordId: String){
        viewModel.getDetailWord(wordId).observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.tvWord.text = result.data?.word
                        result.data?.categories?.forEach { word ->
                            binding.tvType.text = word?.category
                            binding.tvTranslate.text = word?.translate
                            binding.tvResultDescription.text = word?.description
                            binding.tvResultExample.text = word?.example
                        }
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }


}