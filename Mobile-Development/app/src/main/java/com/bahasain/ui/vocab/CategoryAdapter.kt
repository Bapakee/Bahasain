package com.bahasain.ui.vocab

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.ui.vocab.category.WordCategoryActivity
import com.dicoding.bahasain.databinding.ItemCategoryVocabBinding

class CategoryAdapter: ListAdapter<CategoryModel, CategoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemCategoryVocabBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category : CategoryModel){
            binding.title.text = category.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryVocabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, WordCategoryActivity::class.java)
            intent.putExtra("WORD_CATEGORIES", category.keyCategories)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryModel>() {
            override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: CategoryModel,
                newItem: CategoryModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
