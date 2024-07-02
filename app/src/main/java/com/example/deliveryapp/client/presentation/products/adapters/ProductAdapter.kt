package com.example.deliveryapp.client.presentation.products.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.databinding.ItemProductBinding

class ProductAdapter(
    private val onProductSelected: (Product) -> Unit
): ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallBack){
    companion object DiffCallBack: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }
    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product, onProductSelected:(Product) -> Unit){
            Glide.with(binding.root).load(product.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivProduct)
            binding.apply {
                tvNameProduct.text = product.name
                tvRating.text = product.ranting.toString()
                tvDescription.text = product.description
                tvPrice.text = binding.root.context.getString(R.string.price_product_text, product.price.toString())
                cvProduct.setOnClickListener {
                    onProductSelected(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(
            getItem(position), onProductSelected = onProductSelected
        )
    }
}