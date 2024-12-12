package com.bahasain.ui.learn.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.learn.LearnViewModel
import com.bahasain.ui.placement.PlacementResultActivity
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityQuizBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.ceil
import kotlin.math.floor

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: QuizAdapter
    private lateinit var quizzes: List<QuizModel>

    private lateinit var moduleId: String
    private lateinit var moduleLevel: String

    private val userAnswers = mutableMapOf<Int, Pair<Any?, Boolean?>>()
    private val answeredQuizzes = mutableSetOf<Int>()

    private var lastFeedbackStatus: Boolean? = null
    private var lastQuizId: Int? = null

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
                            when (quiz.type) {
                                "option" -> QuizModel(
                                    id = quiz.id ?: 0,
                                    type = quiz.type,
                                    question = quiz.question.orEmpty(),
                                    imageUrl = quiz.imageUrl.toString(),
                                    quizOptions = quiz.quizOptions?.map { it?.option.orEmpty() } ?: emptyList(),
                                    correctOption = quiz.answer?.toIntOrNull()
                                )
                                "rearrange" -> QuizModel(
                                    id = quiz.id ?: 0,
                                    type = quiz.type,
                                    question = quiz.question.orEmpty(),
                                    imageUrl = quiz.imageUrl.toString(),
                                    quizOptions = quiz.answer?.split(" ") ?: emptyList(),
                                    correctOrder = quiz.answer?.split(" ")
                                )
                                else -> null
                            }
                        }
                    }
                    setupQuizAdapter()
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupQuizAdapter() {
        adapter = QuizAdapter(
            quizzes,
            answeredQuizzes,
            onOptionAnswerSubmitted = { quizId, selectedOption ->
                answeredQuizzes.add(quizId)
                val currentQuiz = quizzes.first { it.id == quizId }
                val isCorrect = validateAnswer(currentQuiz, selectedOption)
                userAnswers[quizId] = Pair(selectedOption, isCorrect)
                showFeedBack(isCorrect, currentQuiz)
            },
            onRearrangeAnswerSubmitted = { quizId, userOrder ->
                answeredQuizzes.add(quizId)
                val currentQuiz = quizzes.first { it.id == quizId }
                val isCorrect = validateAnswer(currentQuiz, userOrder)
                userAnswers[quizId] = Pair(userOrder, isCorrect)
                showFeedBack(isCorrect, currentQuiz)
            }
        )
        viewPager.adapter = adapter
        TabLayoutMediator(binding.indicator, viewPager) { _, _ -> }.attach()
    }

    private fun validateAnswer(quiz: QuizModel, userAnswer: Any?): Boolean {
        return when (quiz.type) {
            "option" -> userAnswer == quiz.correctOption
            "rearrange" -> userAnswer == quiz.correctOrder
            else -> false
        }
    }

    private fun calculateScore(): Int {
        var score = 0
        userAnswers.forEach { (_, answer) ->
            if (answer.second == true) {
                score++
            }
        }
        return ceil((score.toDouble() / quizzes.size) * 100).toInt()
    }

    private fun nextQuiz() {
        if (viewPager.currentItem < quizzes.size - 1) {
            viewPager.currentItem += 1
            val currentQuizId = quizzes[viewPager.currentItem].id
            val userAnswer = userAnswers[currentQuizId]
            if (userAnswer != null) {
                val (_, isCorrect) = userAnswer
                if (isCorrect != null) {
                    lastFeedbackStatus = isCorrect
                    lastQuizId = currentQuizId
                    showFeedBack(lastFeedbackStatus!!, quizzes.first { it.id == lastQuizId!! })
                }
            } else {
                binding.feedbackContainer.visibility = View.GONE
            }
        }else{
            if (viewPager.currentItem == quizzes.size - 1) {
                val score = calculateScore()
                Toast.makeText(this, "Skor Anda: $score/${quizzes.size}", Toast.LENGTH_SHORT).show()

                putProgress(moduleId.toInt(), moduleLevel.toInt(), score)

                val intent = Intent(this, QuizResultActivity::class.java)
                intent.putExtra("SCORE_QUIZ", score)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun putProgress(moduleId: Int, levelId: Int, score: Int){
        viewModel.putProgress(moduleId, levelId, score).observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun backQuiz() {
        if (viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
            binding.feedbackContainer.visibility = View.GONE

            val previousQuizId = quizzes[viewPager.currentItem].id
            val userAnswer = userAnswers[previousQuizId]
            if (userAnswer != null) {
                val (answer, isCorrect) = userAnswer
                if (isCorrect != null) {
                    lastFeedbackStatus = isCorrect
                    lastQuizId = previousQuizId
                    showFeedBack(lastFeedbackStatus!!, quizzes.first { it.id == lastQuizId!! })
                }
            }
        }
    }

    private fun showFeedBack(isCorrect: Boolean, quiz: QuizModel) {
        binding.feedbackContainer.visibility = View.VISIBLE

        if (isCorrect) {
            binding.feedbackContainer.setBackgroundResource(R.drawable.bg_correct)
            binding.feedbackText.setTextColor(ContextCompat.getColor(this, R.color.blue))
            binding.feedbackText.text = getString(R.string.correct_answer)
            binding.btnContinue.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
        } else {
            binding.feedbackContainer.setBackgroundResource(R.drawable.bg_incorrect)
            binding.feedbackText.setTextColor(ContextCompat.getColor(this, R.color.orange))
            binding.feedbackText.text = getString(R.string.incorrect_answer)
            binding.btnContinue.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
        }

        binding.btnContinue.setOnClickListener { nextQuiz() }

        lastFeedbackStatus = isCorrect
        lastQuizId = quiz.id
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
