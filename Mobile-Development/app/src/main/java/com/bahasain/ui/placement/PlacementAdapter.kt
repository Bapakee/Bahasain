package com.bahasain.ui.placement

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemMathcingBinding
import com.dicoding.bahasain.databinding.ItemMultipleChoiceBinding
import com.dicoding.bahasain.databinding.ItemSingleChoiceBinding

class PlacementAdapter(
    private val placementQuiz: List<Placement>,
    private val onContinueClicked: (Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SINGLE_CHOICE -> {
                val binding = ItemSingleChoiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SingleChoiceViewHolder(binding)
            }

            VIEW_TYPE_MULTIPLE_CHOICE -> {
                val binding = ItemMultipleChoiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MultipleChoiceViewHolder(binding)
            }

            VIEW_TYPE_MATCHING -> {
                val binding = ItemMathcingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MatchingViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = placementQuiz[position]
        when (holder) {
            is SingleChoiceViewHolder -> {
                println("Binding SingleChoice: $quiz")
                holder.bind(quiz as Placement.SingleChoice, onContinueClicked)
            }

            is MultipleChoiceViewHolder -> {
                println("Binding MultipleChoice: $quiz")
                holder.bind(quiz as Placement.MultipleChoice, onContinueClicked)
            }

            is MatchingViewHolder -> {
                println("Binding Matching: $quiz")
                holder.bind(quiz as Placement.Matching, onContinueClicked)
            }
        }
    }


    override fun getItemCount(): Int = placementQuiz.size

    override fun getItemViewType(position: Int): Int {
        return when (placementQuiz[position]) {
            is Placement.SingleChoice -> VIEW_TYPE_SINGLE_CHOICE
            is Placement.MultipleChoice -> VIEW_TYPE_MULTIPLE_CHOICE
            is Placement.Matching -> VIEW_TYPE_MATCHING
        }
    }

    class SingleChoiceViewHolder(private val binding: ItemSingleChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Placement.SingleChoice, onContinueClicked: (Boolean) -> Unit) {
            binding.quizTitle.text = quiz.quizTitle
            binding.quiz.text = quiz.quiz

            quiz.optionsQuiz.forEachIndexed { index, option ->
                val radioButton = RadioButton(binding.root.context).apply {
                    text = option
                    id = index
                }
                binding.rgQuiz.addView(radioButton)
            }

            binding.rgQuiz.setOnCheckedChangeListener { _, checkedId ->
                val isCorrect = checkedId == quiz.correctAnswer
                onContinueClicked(isCorrect)
            }
        }
    }

    class MultipleChoiceViewHolder(private val binding: ItemMultipleChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Placement.MultipleChoice, onContinueClicked: (Boolean) -> Unit) {
            binding.quizTitle.text = quiz.quizTitle
            binding.quiz.text = quiz.quiz

            val selectedIndices = mutableListOf<Int>()

            quiz.optionsQuiz.forEachIndexed { index, option ->
                val checkBox = CheckBox(binding.root.context).apply {
                    text = option
                    id = index
                }
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) selectedIndices.add(index)
                    else selectedIndices.remove(index)
                }
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) selectedIndices.add(index)
                    else selectedIndices.remove(index)

                    // Periksa apakah jawaban sudah benar
                    val isCorrect = selectedIndices == quiz.correctAnswer.toSet()
                    onContinueClicked(isCorrect)
                }
                binding.llOptions.addView(checkBox)
            }
        }
    }

    class MatchingViewHolder(private val binding: ItemMathcingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Placement.Matching, onContinueClicked: (Boolean) -> Unit) {
            binding.quizTitle.text = quiz.quizTitle

            quiz.pairs.forEach { (word, meaning) ->
                val wordView = TextView(binding.root.context).apply {
                    text = word
                    setBackgroundResource(R.drawable.checkbox_background)
                }
                val meaningView = TextView(binding.root.context).apply {
                    text = meaning
                    setBackgroundResource(R.drawable.checkbox_background)
                }
                binding.leftColumn.addView(wordView)
                binding.rightColumn.addView(meaningView)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_SINGLE_CHOICE = 1
        const val VIEW_TYPE_MULTIPLE_CHOICE = 2
        const val VIEW_TYPE_MATCHING = 3
    }
}

