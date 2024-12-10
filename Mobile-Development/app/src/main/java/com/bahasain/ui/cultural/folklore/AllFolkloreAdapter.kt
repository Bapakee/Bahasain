package com.bahasain.ui.cultural.folklore

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.cultural.DataItemFolklore
import com.bumptech.glide.Glide
import com.dicoding.bahasain.databinding.ItemViewFolkloreCulturalBinding

class AllFolkloreAdapter :
    ListAdapter<DataItemFolklore, AllFolkloreAdapter.FolkloreViewHolder>(DIFF_CALLBACK) {

    class FolkloreViewHolder(private val binding: ItemViewFolkloreCulturalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(folklore: DataItemFolklore) {
            Glide.with(binding.root.context)
                .load(folklore.imageUrl)
                .into(binding.ivFolklore)

            binding.tvTitle.text = folklore.title
            binding.tvLocation.text = folklore.origin
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolkloreViewHolder {
        val binding = ItemViewFolkloreCulturalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FolkloreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolkloreViewHolder, position: Int) {
        val folklore = getItem(position)
        holder.bind(folklore)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailFolkloreActivity::class.java)
            intent.putExtra("FOLKLORE_ID", folklore.id.toString())
            holder.itemView.context.startActivity(intent)
        }
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