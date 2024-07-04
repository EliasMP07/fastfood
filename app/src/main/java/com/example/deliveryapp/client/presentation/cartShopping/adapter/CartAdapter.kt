package com.example.deliveryapp.client.presentation.cartShopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.databinding.ItemCartBinding

class CartAdapter(
    private val onRemoveProduct: (Product) -> Unit,
    private val removeQuantityProduct: (Product) -> Unit,
    private val addQuantityProduct: (Product) -> Unit,
) : ListAdapter<Product, CartAdapter.CartViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: Product,
            onRemoveProduct: (Product) -> Unit,
            removeQuantityProduct: (Product) -> Unit,
            addQuantityProduct: (Product) -> Unit
        ) {
            Glide.with(binding.root).load(product.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivProduct)
            binding.apply {
                tvNameProduct.text = product.name
                tvPriceUnique.text = binding.root.context.getString(R.string.price_product_text, product.price.toString())
                tvTotalProduct.text = product.quantity.toString()
                btnRemove.setOnClickListener {
                    removeQuantityProduct(product)
                }
                btnDelete.setOnClickListener {
                    onRemoveProduct(product)
                }
                btnAdd.setOnClickListener {
                    addQuantityProduct(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(
            getItem(position), onRemoveProduct = onRemoveProduct, removeQuantityProduct = removeQuantityProduct, addQuantityProduct = addQuantityProduct
        )
    }
}
