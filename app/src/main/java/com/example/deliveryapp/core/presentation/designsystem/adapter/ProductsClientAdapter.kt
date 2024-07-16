package com.example.deliveryapp.core.presentation.designsystem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ItemProductOrderClientBinding
import com.example.deliveryapp.core.domain.model.order.ProductClient

class ProductsClientAdapter(

): ListAdapter<ProductClient, ProductsClientAdapter.ProductClientViewHolder>(DiffCallBack){

    companion object DiffCallBack: DiffUtil.ItemCallback<ProductClient>(){
        override fun areItemsTheSame(oldItem: ProductClient, newItem: ProductClient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductClient, newItem: ProductClient): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class ProductClientViewHolder(
        private val binding: ItemProductOrderClientBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(productClient: ProductClient){
            binding.apply {
                tvNameProduct.text = productClient.name
                tvTotalProduct.text = binding.root.context.getString(R.string.total_products, productClient.quantity.toString())
            }
            Glide.with(binding.root).load(productClient.image1).into(binding.ivProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductClientViewHolder {
        return ProductClientViewHolder(
            ItemProductOrderClientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductClientViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }
}