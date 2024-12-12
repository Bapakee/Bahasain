package com.bahasain.ui.cultural.historical

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.data.remote.response.cultural.DataItemHistorical
import com.bumptech.glide.Glide
import com.dicoding.bahasain.databinding.ItemViewHistoricalCulturalBinding

class AllHistoricalAdapter :
    ListAdapter<DataItemHistorical, AllHistoricalAdapter.AllHistoricalViewHolder>(DIFF_CALLBACK) {

    class AllHistoricalViewHolder(private val binding: ItemViewHistoricalCulturalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historical: DataItemHistorical) {
            Glide.with(binding.root.context)
                .load(historical.imageUrl)
                .into(binding.ivCulture)

            binding.tvTitle.text = historical.title
            binding.tvLocation.text = historical.mapLocation
            binding.tvDescHistorical.text = historical.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllHistoricalViewHolder {
        val binding = ItemViewHistoricalCulturalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AllHistoricalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllHistoricalViewHolder, position: Int) {
        val historical = getItem(position)
        holder.bind(historical)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailHistoricalActivity::class.java)
            intent.putExtra("HISTORICAL_ID", historical.id.toString())
            holder.itemView.context.startActivity(intent)
        }
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