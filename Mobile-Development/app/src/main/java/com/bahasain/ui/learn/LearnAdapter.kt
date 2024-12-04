package com.bahasain.ui.learn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.DataItem
import com.dicoding.bahasain.databinding.ItemLearnBinding

class LearnAdapter : ListAdapter<DataItem, LearnAdapter.ModuleViewHolder>(DIFF_CALLBACK) {

    class ModuleViewHolder(private val binding: ItemLearnBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(module : DataItem){
            binding.tvLessonsInfo.text = "Module - ${module.level.toString()}"
            binding.tvTotal.text = module.lessonsCompleted

            val lessonAdapter = LessonAdapter()

            lessonAdapter.submitList(module.levels)

            binding.rvLesson.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvLesson.adapter = lessonAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ItemLearnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = getItem(position)
        holder.bind(module)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}