package com.bahasain.ui.cultural

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.DataItemFolklore
import com.bumptech.glide.Glide
import com.dicoding.bahasain.databinding.ItemFolkloreCulturalBinding

class FolkloreAdapter :
    ListAdapter<DataItemFolklore, FolkloreAdapter.FolkloreViewHolder>(DIFF_CALLBACK) {

    class FolkloreViewHolder(private val binding: ItemFolkloreCulturalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(folklore: DataItemFolklore) {
            Glide.with(binding.root.context)
                .load(folklore.imageUrl)
                .into(binding.ivFolklore)

            binding.tvTitle.text = folklore.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolkloreViewHolder {
        val binding = ItemFolkloreCulturalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FolkloreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolkloreViewHolder, position: Int) {
        val folklore = getItem(position)
        holder.bind(folklore)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemFolklore>() {
            override fun areItemsTheSame(
                oldItem: DataItemFolklore,
                newItem: DataItemFolklore
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemFolklore,
                newItem: DataItemFolklore
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}