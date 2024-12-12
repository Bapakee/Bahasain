package com.bahasain.ui.cultural

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.cultural.DataItemRecipe
import com.bahasain.ui.cultural.recipe.DetailRecipeActivity
import com.bumptech.glide.Glide
import com.dicoding.bahasain.databinding.ItemRecipeCulturalBinding

class RecipeAdapter : ListAdapter<DataItemRecipe, RecipeAdapter.RecipeViewHolder>(DIFF_CALLBACK) {
    class RecipeViewHolder(private val binding: ItemRecipeCulturalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataItemRecipe) {
            Glide.with(binding.root.context)
                .load(recipe.imageUrl)
                .into(binding.ivRecipe)

            binding.tvTitle.text = recipe.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeCulturalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailRecipeActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemRecipe>() {
            override fun areItemsTheSame(
                oldItem: DataItemRecipe,
                newItem: DataItemRecipe
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemRecipe,
                newItem: DataItemRecipe
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}