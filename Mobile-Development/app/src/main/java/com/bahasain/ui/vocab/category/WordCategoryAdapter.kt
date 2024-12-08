package com.bahasain.ui.vocab.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bahasain.databinding.ItemCategoriesBinding
import com.dicoding.bahasain.databinding.ItemWordCategoriesBinding

class WordCategoryAdapter : ListAdapter<WordCategoryModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

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
            is WordCategoryModel.WordItem -> (holder as WordItemViewHolder).bind(item)
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
}
