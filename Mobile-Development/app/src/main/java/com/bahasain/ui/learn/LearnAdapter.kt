package com.bahasain.ui.learn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.learn.DataItemLearn
import com.bahasain.ui.setLevel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemLearnBinding

class LearnAdapter : ListAdapter<LearnItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val TYPE_HEADING = 0
        private const val TYPE_MODULE = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LearnItem>() {
            override fun areItemsTheSame(oldItem: LearnItem, newItem: LearnItem): Boolean {
                return when {
                    oldItem is LearnItem.Heading && newItem is LearnItem.Heading -> oldItem.level == newItem.level
                    oldItem is LearnItem.Module && newItem is LearnItem.Module -> oldItem.data?.id == newItem.data?.id
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: LearnItem, newItem: LearnItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is LearnItem.Heading -> TYPE_HEADING
            is LearnItem.Module -> TYPE_MODULE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_heading_learn, parent, false)
                HeadingViewHolder(view)
            }
            TYPE_MODULE -> {
                val binding = ItemLearnBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ModuleViewHolder(binding, parent.context)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is LearnItem.Heading -> (holder as HeadingViewHolder).bind(item)
            is LearnItem.Module -> (holder as ModuleViewHolder).bind(item.data)
        }
    }

    class HeadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(heading: LearnItem.Heading) {
            itemView.findViewById<TextView>(R.id.tvHeading).text =
                setLevel(heading.level)
        }
    }

    class ModuleViewHolder(private val binding: ItemLearnBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(module: DataItemLearn?) {
            binding.tvTitleModule.text =
                context.getString(R.string.title_module, module?.id.toString())
            binding.tvTotal.text = module?.lessonsCompleted
            binding.tvChapter.text = module?.name

            val lessonAdapter = LessonAdapter()
            lessonAdapter.submitList(module?.levels)
            binding.rvLesson.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvLesson.adapter = lessonAdapter
        }
    }
}
