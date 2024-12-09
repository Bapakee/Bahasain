package com.bahasain.ui.vocab.category

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.vocab.VocabViewModel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityWordCategoryBinding


class WordCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordCategoryBinding

    private lateinit var categories: String

    private lateinit var adapter: WordCategoryAdapter

    private var originalList: List<WordCategoryModel> = emptyList()

    private val viewModel by viewModels<VocabViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWordCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvWord.layoutManager = LinearLayoutManager(this)

        adapter = WordCategoryAdapter()

        binding.rvWord.adapter = adapter

        val searchView = binding.searchView

        searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            .setTextColor(Color.BLACK)

        categories = intent.getStringExtra("WORD_CATEGORIES").toString()
        if (categories.isNotEmpty()) {
            viewModel.getWordCategories(categories)
            observeWordCategories(categories)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { adapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.submitList(originalList) // Kembali ke daftar asli
                } else {
                    adapter.filter(newText)
                }
                return true
            }
        })

        binding.btnBack.setOnClickListener{ onBackPressed() }
    }

    private fun observeWordCategories(categories: String) {
        viewModel.getWordCategories(categories).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val word = result.data?.map { it?.word }
                    val groupedData = groupWordsByAlphabet(word)
                    originalList = groupedData
                    adapter.submitList(groupedData)
                }

                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun groupWordsByAlphabet(words: List<String?>?): List<WordCategoryModel> {
        val groupedData = mutableListOf<WordCategoryModel>()

        words?.filterNotNull()?.sorted()?.groupBy { it.first().uppercaseChar() }
            ?.forEach { (letter, wordList) ->
                groupedData.add(WordCategoryModel.Header(letter.toString()))
                wordList.forEach { word ->
                    groupedData.add(WordCategoryModel.WordItem(word))
                }
            }

        return groupedData
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}