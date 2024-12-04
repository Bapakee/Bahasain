package com.bahasain.ui.placement

import android.content.ClipData
import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemMathcingChoiceBinding
import com.dicoding.bahasain.databinding.ItemMultipleChoiceBinding
import com.dicoding.bahasain.databinding.ItemSingleChoiceBinding

class PlacementAdapter(
    private val placementQuiz: List<Placement>,
    private val onOptionsSelected: (selectedOption: Any) -> Unit
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
                val binding = ItemMathcingChoiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MatchingViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = placementQuiz[position]
        when (holder) {
            is SingleChoiceViewHolder -> {
                holder.bind(quiz as Placement.SingleChoice, onOptionsSelected)
            }

            is MultipleChoiceViewHolder -> {
                holder.bind(quiz as Placement.MultipleChoice, onOptionsSelected)
            }

            is MatchingViewHolder -> {
                holder.bind(quiz as Placement.Matching, onOptionsSelected)
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
        fun bind(
            quiz: Placement.SingleChoice,
            onOptionsSelected: (selectedOption: List<Int>) -> Unit
        ) {
            binding.quizTitle.text = quiz.quizTitle
            binding.quiz.text = quiz.quiz

            binding.rgQuiz.removeAllViews()

            quiz.optionsQuiz.forEachIndexed { index, option ->
                val radioButton = LayoutInflater.from(binding.root.context)
                    .inflate(R.layout.item_radio, binding.rgQuiz, false) as RadioButton

                radioButton.apply {
                    text = option
                    id = index
                }

                radioButton.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        quiz.userAnswer = index
                        onOptionsSelected(listOf(index))
                    }
                }
                binding.rgQuiz.addView(radioButton)
            }
        }
    }

    class MultipleChoiceViewHolder(private val binding: ItemMultipleChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            quiz: Placement.MultipleChoice,
            onOptionsSelected: (selectedOption: List<Int>) -> Unit
        ) {
            binding.quizTitle.text = quiz.quizTitle
            binding.quiz.text = quiz.quiz

            val selectedIndices = mutableListOf<Int>()

            quiz.optionsQuiz.forEachIndexed { index, option ->
                val checkBox = LayoutInflater.from(binding.root.context)
                    .inflate(R.layout.item_checkbox, binding.llOptions, false) as CheckBox

                checkBox.apply {
                    text = option
                    id = index
                }
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedIndices.add(index)
                    } else {
                        selectedIndices.remove(index)
                    }
                    quiz.userAnswers = selectedIndices.toList()
                    onOptionsSelected(selectedIndices)
                }
                binding.llOptions.addView(checkBox)
            }
        }
    }

    class MatchingViewHolder(private val binding: ItemMathcingChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val userMatches = mutableMapOf<String, String>()

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(quiz: Placement.Matching, onOptionsSelected: (selectedOption: List<Int>) -> Unit) {
            binding.quizTitle.text = quiz.quizTitle
            binding.tvQuiz.text = quiz.quiz

            quiz.pairs.forEach { (word, _) ->
                val textView = inflateLeftTextView(word)
                binding.leftColumn.addView(textView)
            }

            quiz.pairs.forEach { (word, _) ->
                val textView = inflateRightTextView("")
                textView.tag = word
                binding.rightColumn.addView(textView)
            }

            quiz.pairs.map { it.second }.shuffled().forEach { meaning ->
                val textView = inflateAnswerTextView(meaning)
                textView.setOnLongClickListener {
                    val clipData = ClipData.newPlainText("text", meaning)
                    val dragShadow = View.DragShadowBuilder(it)
                    it.startDragAndDrop(clipData, dragShadow, null, 0)
                    true
                }
                binding.answerColumn.addView(textView)
            }

            binding.rightColumn.children.forEach { view ->
                view.setOnDragListener { _, event ->
                    if (event.action == DragEvent.ACTION_DROP) {
                        val draggedText = event.clipData.getItemAt(0).text.toString()
                        val word = (view as TextView).tag?.toString()
                        if (word != null) {
                            userMatches[word] = draggedText
                            quiz.userMatches = userMatches
                            onOptionsSelected(
                                quiz.pairs.mapIndexedNotNull { index, pair ->
                                    if (userMatches[pair.first] == pair.second) index else null
                                }
                            )
                        } else {
                            Log.e("Debug", "Tag is null for view in rightColumn")
                        }
                        view.text = draggedText
                    }
                    true
                }
            }
        }

        private fun inflateLeftTextView(text: String): TextView {
            return (LayoutInflater.from(binding.root.context)
                .inflate(R.layout.item_left_matching, binding.leftColumn, false) as TextView).apply {
                this.text = text
            }
        }

        private fun inflateRightTextView(text: String): TextView {
            return (LayoutInflater.from(binding.root.context)
                .inflate(R.layout.item_right_matching, binding.rightColumn, false) as TextView).apply {
                this.text = text
            }
        }

        private fun inflateAnswerTextView(text: String): TextView {
            return (LayoutInflater.from(binding.root.context)
                .inflate(R.layout.item_answer_matching, binding.answerColumn, false) as TextView).apply {
                this.text = text
            }
        }

    }

    companion object {
        const val VIEW_TYPE_SINGLE_CHOICE = 1
        const val VIEW_TYPE_MULTIPLE_CHOICE = 2
        const val VIEW_TYPE_MATCHING = 3
    }
}
