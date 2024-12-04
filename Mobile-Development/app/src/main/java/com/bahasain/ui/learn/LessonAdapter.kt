package com.bahasain.ui.learn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.LevelsItem
import com.dicoding.bahasain.databinding.ItemLessonCardListBinding

class LessonAdapter : ListAdapter<LevelsItem, LessonAdapter.LessonViewHolder>(DIFF_CALLBACK) {

    class LessonViewHolder(private val binding: ItemLessonCardListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson : LevelsItem){
            binding.tvLessonName.text = lesson.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = getItem(position)
        holder.bind(lesson)
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
