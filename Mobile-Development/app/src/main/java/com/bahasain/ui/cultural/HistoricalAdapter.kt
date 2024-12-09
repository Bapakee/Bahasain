package com.bahasain.ui.cultural

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.DataItemHistorical
import com.bumptech.glide.Glide
import com.dicoding.bahasain.databinding.ItemHistoricalCulturalBinding

class HistoricalAdapter :
    ListAdapter<DataItemHistorical, HistoricalAdapter.HistoricalViewHolder>(DIFF_CALLBACK) {

    class HistoricalViewHolder(private val binding: ItemHistoricalCulturalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historical: DataItemHistorical) {
            Glide.with(binding.root.context)
                .load(historical.imageUrl)
                .into(binding.ivCulture)

            binding.tvTitle.text = historical.title
            binding.tvLocation.text = historical.mapLocation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalViewHolder {
        val binding = ItemHistoricalCulturalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoricalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricalViewHolder, position: Int) {
        val historical = getItem(position)
        holder.bind(historical)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistorical>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistorical,
                newItem: DataItemHistorical
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistorical,
                newItem: DataItemHistorical
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}