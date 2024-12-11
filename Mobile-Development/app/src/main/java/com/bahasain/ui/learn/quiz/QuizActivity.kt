package com.bahasain.ui.learn.quiz

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.learn.LearnViewModel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityQuizBinding
import com.google.android.material.tabs.TabLayoutMediator

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: QuizAdapter
    private lateinit var quizzes: List<QuizModel>

    private lateinit var moduleId: String
    private lateinit var moduleLevel: String

    private val userAnswers = mutableMapOf<Int, Int?>()

    private val viewModel by viewModels<LearnViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false

        moduleId = intent.getStringExtra("MODULE_ID") ?: ""
        moduleLevel = intent.getStringExtra("MODULE_LEVEL") ?: ""

        if (moduleId.isNotEmpty() && moduleLevel.isNotEmpty()) {
            viewModel.setModuleId(moduleId, moduleLevel)
            getQuizzes(moduleId, moduleLevel)
        }

        binding.btnBack.setOnClickListener { backQuiz() }

        binding.feedbackContainer.visibility = View.GONE
    }

    private fun getQuizzes(moduleId: String, moduleLevel: String) {
        viewModel.getQuiz(moduleId, moduleLevel).observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    quizzes = result.data.flatMap { levelContent ->
                        levelContent.quizzes.mapNotNull { quiz ->
                            QuizModel(
                                id = quiz.id ?: 0,
                                question = quiz.question.orEmpty(),
                                imageUrl = quiz.imageUrl?.toString().orEmpty(),
                                quizOptions = quiz.quizOptions?.map { it?.option.orEmpty() } ?: emptyList(),
                                answer = quiz.answer?.toInt() ?: -1
                            )
                        }
                    }
                    setupQuizAdapter()
                }
                is Result.Error -> showLoading(false)
            }
        }
    }

    private fun setupQuizAdapter() {
        adapter = QuizAdapter(quizzes) { quizId, selectedOption ->
            userAnswers[quizId] = selectedOption
            val currentQuiz = quizzes.first { it.id == quizId }
            val isCorrect = selectedOption == currentQuiz.answer

            showFeedBack(isCorrect, currentQuiz)
        }

        viewPager.adapter = adapter

        TabLayoutMediator(binding.indicator, viewPager) { _, _ -> }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Get the current quiz based on the selected position
                val currentQuiz = quizzes[position]
                val userAnswer = userAnswers[currentQuiz.id]

                // Check if the answer is correct
                val isCorrect = userAnswer == currentQuiz.answer

                showFeedBack(isCorrect, currentQuiz)
            }
        })
    }

    private fun nextQuiz() {
        if (viewPager.currentItem < quizzes.size - 1) {
            viewPager.currentItem += 1
        }
    }

    private fun backQuiz() {
        if (viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        }
    }

    private fun showFeedBack(isCorrect: Boolean, quiz: QuizModel) {
        binding.feedbackContainer.visibility = View.VISIBLE

        if (isCorrect){
            binding.feedbackContainer.setBackgroundColor(Color.BLUE)
            binding.feedbackText.setTextColor(Color.BLUE)
        }else{
            binding.feedbackContainer.setBackgroundColor(Color.RED)
            binding.feedbackText.setTextColor(Color.RED)
        }

        binding.btnContinue.setOnClickListener { nextQuiz() }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
