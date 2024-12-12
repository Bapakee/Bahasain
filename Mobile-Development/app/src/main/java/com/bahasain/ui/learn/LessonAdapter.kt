package com.bahasain.ui.learn

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.learn.LevelsItem
import com.bahasain.ui.learn.quiz.QuizActivity
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemLessonCardListBinding

class LessonAdapter : ListAdapter<LevelsItem, LessonAdapter.LessonViewHolder>(DIFF_CALLBACK) {

    class LessonViewHolder(private val binding: ItemLessonCardListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson : LevelsItem){
            binding.tvLessonName.text = lesson.title

            if (lesson.isCompleted == true){
                binding.ivLessonImage.setImageResource(R.drawable.check_done_circle)
            }

            if (lesson.isAccessible == false) {
                binding.root.alpha = 0.5f
                binding.root.isEnabled = false
                binding.ivLessonImage.setImageResource(R.drawable.baseline_lock_24)
            } else {
                binding.root.alpha = 1f
                binding.root.isEnabled = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = getItem(position)
        holder.bind(lesson)

        holder.itemView.setOnClickListener{
            if (lesson.isAccessible == true){
                val moduleId = lesson.moduleId
                val moduleLevel = lesson.id

                val intent = Intent(holder.itemView.context, QuizActivity::class.java).apply {
                    putExtra("MODULE_ID", moduleId.toString())
                    putExtra("MODULE_LEVEL", moduleLevel.toString())
                }

                holder.itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LevelsItem>() {
            override fun areItemsTheSame(oldItem: LevelsItem, newItem: LevelsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LevelsItem,
                newItem: LevelsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
