package com.bahasain.ui.learn

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.learn.DataItemLearn
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemLearnBinding

class LearnAdapter : ListAdapter<DataItemLearn, LearnAdapter.ModuleViewHolder>(DIFF_CALLBACK) {

    class ModuleViewHolder(private val binding: ItemLearnBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(module: DataItemLearn) {
            binding.tvTitleModule.text = context.getString(R.string.title_module, module.id.toString())
            binding.tvTotal.text = module.lessonsCompleted
            binding.tvChapter.text = module.name

            val lessonAdapter = LessonAdapter()

            lessonAdapter.submitList(module.levels)

            binding.rvLesson.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvLesson.adapter = lessonAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ItemLearnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = getItem(position)
        holder.bind(module)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemLearn>() {
            override fun areItemsTheSame(oldItem: DataItemLearn, newItem: DataItemLearn): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemLearn,
                newItem: DataItemLearn
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}