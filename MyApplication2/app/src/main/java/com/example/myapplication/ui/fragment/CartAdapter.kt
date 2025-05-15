package com.example.myapplication.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.local.CartItemEntity
import com.example.myapplication.databinding.ItemCartBinding

class CartAdapter(
    private val onDelete: (CartItemEntity) -> Unit
) : ListAdapter<CartItemEntity, CartAdapter.ViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CartItemEntity>() {
            override fun areItemsTheSame(a: CartItemEntity, b: CartItemEntity) =
                a.cartItemId == b.cartItemId

            override fun areContentsTheSame(a: CartItemEntity, b: CartItemEntity) =
                a == b
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onDelete)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemCartBinding,
        private val onDelete: (CartItemEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItemEntity) {
            binding.tvTeam.text = item.team

            // Precio desde recurso para poder traducir/formatear apropiadamente
            val ctx = binding.root.context
            binding.tvPrice.text =
                ctx.getString(R.string.price_format, item.price)

            Glide.with(binding.ivJersey.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.ivJersey)

            binding.btnDelete.setOnClickListener { onDelete(item) }
        }
    }
}

