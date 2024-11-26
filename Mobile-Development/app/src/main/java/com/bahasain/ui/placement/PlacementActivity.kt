package com.bahasain.ui.placement

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityPlacementBinding
import com.dicoding.bahasain.databinding.ItemMathcingBinding
import com.dicoding.bahasain.databinding.ItemMultipleChoiceBinding
import com.dicoding.bahasain.databinding.ItemSingleChoiceBinding

class PlacementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PlacementAdapter
    private var currentQuizIndex = 0

    private val placementQuiz = listOf(
        Placement.Matching(
            quizTitle = "Vocabulary Matching",
            quiz = "Pasangkan kata dengan artinya",
            pairs = listOf(
                "Rumah" to "Book",
                "Makan" to "Eat",
                "Buku" to "Sleep",
                "Tidur" to "House"
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

        // Inisialisasi adapter
        adapter = PlacementAdapter(placementQuiz) { isCorrect ->
            goToNextQuiz()
        }

        // Hubungkan adapter ke ViewPager2
        viewPager.adapter = adapter

        binding.btnContinue.setOnClickListener {
            goToNextQuiz()
        }
    }


    private fun goToNextQuiz() {
        if (currentQuizIndex < placementQuiz.size - 1) {
            currentQuizIndex++
            viewPager.setCurrentItem(currentQuizIndex, true)
        } else {
            // Quiz selesai, tampilkan skor
        }
    }

}