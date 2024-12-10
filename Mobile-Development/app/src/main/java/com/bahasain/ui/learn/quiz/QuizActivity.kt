package com.bahasain.ui.learn.quiz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.data.Result
import com.bahasain.data.remote.response.learn.DataItemQuiz
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.cultural.CulturalViewModel
import com.bahasain.ui.learn.LearnViewModel
import com.bahasain.ui.placement.Pair
import com.bahasain.ui.placement.Placement
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityQuizBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    private lateinit var moduleId: String
    private lateinit var moduleLevel: String

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

        moduleId = intent.getStringExtra("MODULE_ID") ?: ""
        moduleLevel = intent.getStringExtra("MODULE_LEVEL") ?: ""
         if (moduleId.isNotEmpty() && moduleLevel.isNotEmpty()){
            viewModel.setModuleId(moduleId, moduleLevel)
        }

        getQUiz(moduleId, moduleLevel)
    }

    private fun getQUiz(moduleId: String, moduleLevel: String){
        viewModel.getQuiz(moduleId, moduleLevel).observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        mapToQuizModel(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun mapToQuizModel(data: List<DataItemQuiz>): List<QuizModel> {
        return data.flatMap { itemQuiz ->
            itemQuiz.quizzes?.mapNotNull { quiz ->
                when (quiz?.type) {
                    "options" -> QuizModel.optionsQuiz(
                        id = quiz.id ?: 0,
                        question = quiz.question.orEmpty(),
                        imageUrl = quiz.imageUrl?.toString().orEmpty(),
                        quizOptions = quiz.quizOptions?.map { it?.option.orEmpty() } ?: emptyList(),
                        answer = quiz.answer?.toInt() ?: -1
                    )
                    else -> null // Abaikan elemen yang tidak dikenal
                }
            } ?: emptyList() // Jika quizzes null, kembalikan list kosong
        }
    }
}