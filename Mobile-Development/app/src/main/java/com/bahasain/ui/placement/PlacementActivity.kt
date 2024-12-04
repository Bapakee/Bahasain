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
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityPlacementBinding
import com.google.android.material.tabs.TabLayoutMediator

class PlacementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PlacementAdapter

    private val viewModel by viewModels<PlacementViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val placementQuiz = listOf(
        Placement.Matching(
            quizTitle = "Vocabulary Matching",
            quiz = "Pasangkan kata dengan artinya",
            pairs = listOf(
                "Rumah" to "House",
                "Makan" to "Eat",
                "Buku" to "Book",
                "Tidur" to "Sleep"
            )
        ),
        Placement.SingleChoice(
            quizTitle = "Grammar",
            quiz = "Pilih kalimat yang benar",
            optionsQuiz = listOf("Saya sudah nasi makan tadi pagi.", "Saya sudah makan nasi tadi pagi.","Sudah makan saya nasi tadi pagi.","I don’t Know"),
            correctAnswer = 1
        ),
        Placement.SingleChoice(
            quizTitle = "Idioms and Expressions",
            quiz = "Apa arti ungkapan \"buah tangan\"?",
            optionsQuiz = listOf("Hasil kebun","Oleh-oleh","Hadiah besar","I don’t Know"),
            correctAnswer = 1
        ),
        Placement.SingleChoice(
            quizTitle = "Complex Sentence Comprehension",
            quiz = "Apa yang dilakukan ibu setelah selesai memasak?",
            optionsQuiz = listOf("Mengajak kami makan", "Pergi ke pasar", "Membersihkan dapur", "Berbicara di ruang tamu", "I don’t Know"),
            correctAnswer = 0
        )
    )

    private val placementAnswers = mutableMapOf<Int, Any>()

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

        
        viewPager = binding.viewPager

        viewPager.isUserInputEnabled = false

        adapter = PlacementAdapter(placementQuiz) { selectedOptions ->
            val currentPosition = viewPager.currentItem

            binding.btnContinue.isEnabled = true
        }

        viewPager.adapter = adapter

        val indicator = binding.indicator
        TabLayoutMediator(indicator, viewPager) { _, _ -> }.attach()

        binding.btnContinue.setOnClickListener {
            nextQuiz()
        }

        binding.btnBack.setOnClickListener{
            backQuiz()
        }

        binding.btnContinue.isEnabled = false
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
                    if (quiz.pairs.all { it.second == quiz.userMatches[it.first] }) {
                        score++
                    }
                }
            }
        }

        return score
    }

    private fun setLevel(){
        val score = calculateScore()
        viewModel.setLevel(score).observe(this) { result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(this, "level anda $score", Toast.LENGTH_SHORT).show()
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
            val score = calculateScore()
            setLevel()

            viewModel.getSession().observe(this) { session ->
                if (session != null) {
                    val updatedSession = session.copy(userLevel = score)
                    viewModel.saveSession(updatedSession)
                } else {
                    Toast.makeText(this, "Gagal memuat sesi", Toast.LENGTH_SHORT).show()
                }
            }

            Toast.makeText(this, "Skor Anda: $score/${placementQuiz.size}", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun backQuiz(){
        if (viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}