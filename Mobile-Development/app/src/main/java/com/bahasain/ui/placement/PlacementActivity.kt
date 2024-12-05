package com.bahasain.ui.placement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bahasain.data.Result
import com.bahasain.ui.MainActivity
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.loadPlacementQuizFromJson
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityPlacementBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlacementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PlacementAdapter

    private lateinit var placementQuiz: List<Placement>

    private val viewModel by viewModels<PlacementViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jsonString = loadPlacementQuizFromJson(this, R.raw.placement_data)
        placementQuiz = parsePlacementQuiz(jsonString)

        viewPager = binding.viewPager

        viewPager.isUserInputEnabled = false

        val quizAnswers = mutableMapOf<Int, List<Int>>()

        adapter = PlacementAdapter(placementQuiz) { quizId, selectedOptions ->
            quizAnswers[quizId] = selectedOptions

            updateContinueButtonState(viewPager.currentItem, quizAnswers)
        }

        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateContinueButtonState(position, quizAnswers)
            }
        })

        val indicator = binding.indicator
        TabLayoutMediator(indicator, viewPager) { _, _ -> }.attach()

        binding.btnContinue.setOnClickListener {
            nextQuiz()
        }

        binding.btnBack.setOnClickListener {
            backQuiz()
        }

        binding.btnContinue.isEnabled = false
    }

    private fun updateContinueButtonState(position: Int, quizAnswers: Map<Int, List<Int>>) {
        val currentSurveyId = position + 1
        val isAnswered = when (val currentQuiz = placementQuiz[position]) {
            is Placement.Matching -> {
                currentQuiz.userMatches.size == currentQuiz.pairs.size
            }

            else -> {
                val selectedOptions = quizAnswers[currentSurveyId] ?: emptyList()
                selectedOptions.isNotEmpty()
            }
        }

        val selectedOptions = quizAnswers[currentSurveyId] ?: emptyList()
        selectedOptions.isNotEmpty()

        binding.btnContinue.isEnabled = isAnswered
    }

    private fun calculateScore(): Int {
        var score = 0

        placementQuiz.forEach { quiz ->
            when (quiz) {
                is Placement.SingleChoice -> {
                    if (quiz.userAnswer == quiz.correctAnswer) {
                        score++
                    }
                }

                is Placement.MultipleChoice -> {
                    if (quiz.userAnswers.containsAll(quiz.correctAnswers) &&
                        quiz.correctAnswers.containsAll(quiz.userAnswers)
                    ) {
                        score++
                    }
                }

                is Placement.Matching -> {
                    if (quiz.pairs.all { it.english == quiz.userMatches[it.indonesia] }) {
                        score++
                    }
                }
            }
        }

        return score
    }

    private fun setLevel() : Int{
        val score = calculateScore()

        val level = when {
            score == placementQuiz.size -> EXPERT_LEVEL
            score == placementQuiz.size - 1 -> INTERMEDIATE_LEVEL
            score <= placementQuiz.size - 2 -> BASIC_LEVEL
            else -> 0
        }

        return level
    }

    private fun updateLevel() {
        viewModel.setLevel(setLevel()).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(this, "level anda ${setLevel()}", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun nextQuiz() {
        if (viewPager.currentItem < placementQuiz.size - 1) {
            viewPager.currentItem += 1
        } else {
            updateLevel()

            viewModel.getSession().observe(this) { session ->
                if (session != null) {
                    val updatedSession = session.copy(userLevel = setLevel())
                    viewModel.saveSession(updatedSession)
                } else {
                    Toast.makeText(this, "Gagal memuat sesi", Toast.LENGTH_SHORT).show()
                }
            }

            Toast.makeText(this, "Skor Anda: ${setLevel()}/${placementQuiz.size}", Toast.LENGTH_LONG)
                .show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun backQuiz() {
        if (viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun parsePlacementQuiz(jsonString: String): List<Placement> {
        val gson = Gson()

        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val rawList: List<Map<String, Any>> = gson.fromJson(jsonString, listType)

        return rawList.map { item ->
            when (val quizType = item["quizType"]) {
                "singleChoice" -> {
                    val id = (item["id"] as Double).toInt()
                    val options = item["optionsQuiz"] as List<String>
                    val correctAnswer = (item["correctAnswer"] as Double).toInt()
                    Placement.SingleChoice(
                        id = id,
                        quizTitle = item["quizTitle"] as String,
                        textReading = item["textReading"] as String,
                        quiz = item["quiz"] as String,
                        optionsQuiz = options,
                        correctAnswer = correctAnswer
                    )
                }

                "matching" -> {
                    val id = (item["id"] as Double).toInt()
                    val pair = (item["pairs"] as List<Map<String, String>>).map {
                        Pair(it["indonesia"]!!, it["english"]!!)
                    }
                    Placement.Matching(
                        id = id,
                        quizTitle = item["quizTitle"] as String,
                        quiz = item["quiz"] as String,
                        pairs = pair
                    )
                }

                else -> throw IllegalArgumentException("Unknown quiz type: $quizType")
            }
        }
    }

    companion object{
        private const val BASIC_LEVEL = 1
        private const val INTERMEDIATE_LEVEL = 2
        private const val EXPERT_LEVEL = 3
    }
}