package com.bahasain.ui.vocab.category

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bahasain.databinding.ItemCategoriesBinding
import com.dicoding.bahasain.databinding.ItemWordCategoriesBinding

class WordCategoryAdapter : ListAdapter<WordCategoryModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var filteredList: List<WordCategoryModel> = currentList

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is WordCategoryModel.Header -> TYPE_HEADER
            is WordCategoryModel.WordItem -> TYPE_WORD_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = ItemWordCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            WordItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is WordCategoryModel.Header -> (holder as HeaderViewHolder).bind(item)
            is WordCategoryModel.WordItem -> {
                (holder as WordItemViewHolder).bind(item)
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, WordDetailActivity::class.java)
                    intent.putExtra("WORD_ID", item.id)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: WordCategoryModel.Header) {
            binding.tvHeader.text = header.title
        }
    }

    inner class WordItemViewHolder(private val binding: ItemWordCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wordItem: WordCategoryModel.WordItem) {
            binding.tvWord.text = wordItem.word
        }
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            currentList
        } else {
            currentList.filter {
                when (it) {
                    is WordCategoryModel.Header -> it.title.contains(query, ignoreCase = true)
                    is WordCategoryModel.WordItem -> it.word.contains(query, ignoreCase = true)
                }
            }
        }
        submitList(filteredList)
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_WORD_ITEM = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WordCategoryModel>() {
            override fun areItemsTheSame(oldItem: WordCategoryModel, newItem: WordCategoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: WordCategoryModel, newItem: WordCategoryModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
