package com.bahasain.ui.survey

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bahasain.ui.placement.PlacementActivity
import com.bahasain.ui.placement.PlacementIntroActivity
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivitySurveyBinding
import com.google.android.material.tabs.TabLayoutMediator

class SurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: SurveyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false

        val survey = listOf(
            SurveyModel(
                id = 1,
                survey = "Why are you interested in learning Bahasa Indonesia?",
                descSurvey = "Select all that apply",
                optionsSurvey = listOf("For academic purposes ", "For work or business", "For travel or cultural exploration", "Just for fun or personal growth"),
                correctAnswer = listOf(0,2)
            ),
            SurveyModel(
                id = 2,
                survey = "Why are you interested in learning Bahasa Indonesia?",
                descSurvey = "Select all that apply",
                optionsSurvey = listOf("For academic purposes ", "For work or business", "For travel or cultural exploration", "Just for fun or personal growth"),
                correctAnswer = listOf(3,2)
            ),
            SurveyModel(
                id = 3,
                survey = "What’s your main goal for learning Bahasa Indonesia?",
                descSurvey = "Select all that apply",
                optionsSurvey = listOf("To become conversationally fluent.", "To read and write effectively in Bahasa Indonesia", "To explore Indonesian culture and traditions", "To improve for academic or professional use"),
                correctAnswer = listOf(3,2)
            ),
            SurveyModel(
                id = 4,
                survey = "When do you usually study?",
                descSurvey = "Select all that apply",
                optionsSurvey = listOf("Morning (08:00–11:00)", "Afternoon (12:00–15:00)", "Evening (18:00–21:00)", "No specific time, I’m flexible"),
                correctAnswer = listOf(3,2)
            )
        )

        val surveyAnswers = mutableMapOf<Int, List<Int>>()

        adapter = SurveyAdapter(survey) { surveyId, selectedOptions ->
            surveyAnswers[surveyId] = selectedOptions

            updateContinueButtonState(viewPager.currentItem, surveyAnswers)
        }

        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateContinueButtonState(position, surveyAnswers)
            }
        })

        val indicator = binding.indicator
        TabLayoutMediator(indicator, viewPager) { _, _ -> }.attach()

        binding.btnBack.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem -= 1
            }
        }

        binding.btnContinue.setOnClickListener {
            if (viewPager.currentItem < survey.size - 1) {
                viewPager.currentItem += 1
            } else {
                startActivity(Intent(this, PlacementIntroActivity::class.java))
                finish()
            }
        }

        binding.btnContinue.isEnabled = false
    }

    private fun updateContinueButtonState(position: Int, surveyAnswers: Map<Int, List<Int>>) {
        val currentSurveyId = position + 1 // Assuming survey IDs are sequential starting from 1
        val selectedOptions = surveyAnswers[currentSurveyId] ?: emptyList()
        binding.btnContinue.isEnabled = selectedOptions.isNotEmpty()
    }
}