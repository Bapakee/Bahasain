package com.bahasain.ui.survey

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemSurveyBinding

class SurveyAdapter(
    private val survey: List<SurveyModel>,
    private val onOptionsSelected: (surveyId: Int, selectedOption: List<Int>) -> Unit
): RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder>(){

    inner class SurveyViewHolder(val binding: ItemSurveyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(survey: SurveyModel){
            binding.survey.text = survey.survey
            binding.descSurvey.text = survey.descSurvey
            binding.llOptions.removeAllViews()

            val selectedIndices = mutableListOf<Int>()

            survey.optionsSurvey.forEachIndexed { index, option ->
                val checkBox = LayoutInflater.from(binding.root.context)
                    .inflate(R.layout.item_checkbox, binding.llOptions, false) as CheckBox

                checkBox.apply {
                    text = option
                    id = index
                }

                checkBox.setOnCheckedChangeListener {  _, isChecked ->
                    if (isChecked) {
                        selectedIndices.add(index)
                    } else {
                        selectedIndices.remove(index)
                    }
                    onOptionsSelected(survey.id, selectedIndices)
                }

                binding.llOptions.addView(checkBox)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val binding = ItemSurveyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SurveyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        holder.bind(survey[position])
    }

    override fun getItemCount(): Int = survey.size

}