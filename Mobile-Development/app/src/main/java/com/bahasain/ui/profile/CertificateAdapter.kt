package com.bahasain.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bahasain.databinding.ItemCertificateBinding

class CertificateAdapter :
    ListAdapter<String, CertificateAdapter.CertificateViewHolder>(DIFF_CALLBACK) {

    class CertificateViewHolder(private val binding: ItemCertificateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(certificateUrl: String) {
            Glide.with(binding.root.context)
                .load(certificateUrl)
                .into(binding.ivCertificate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val binding = ItemCertificateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CertificateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val certificateUrl = getItem(position)
        holder.bind(certificateUrl)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
