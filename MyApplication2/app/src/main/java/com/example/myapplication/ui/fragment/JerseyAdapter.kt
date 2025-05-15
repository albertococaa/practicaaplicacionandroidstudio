package com.example.myapplication.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemJerseyBinding
import com.example.myapplication.model.Jersey

class JerseyAdapter(
    private val onClick: (Jersey) -> Unit
) : RecyclerView.Adapter<JerseyAdapter.ViewHolder>() {

    private val items = mutableListOf<Jersey>()

    fun submitList(list: List<Jersey>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJerseyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(
        private val binding: ItemJerseyBinding,
        private val onClick: (Jersey) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(jersey: Jersey) {
            binding.tvTeam.text = jersey.team
            binding.tvPrice.text = "€%.2f".format(jersey.price)
            Glide.with(binding.ivJersey.context)
                .load(jersey.imageUrl)
                .centerCrop()
                .into(binding.ivJersey)
            binding.root.setOnClickListener { onClick(jersey) }
        }
    }
}
