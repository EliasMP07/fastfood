package com.example.deliveryapp.client.presentation.home.fragments.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.OrderClient
import com.example.deliveryapp.databinding.ItemOrderBinding

class OrdersClientAdapter(

): ListAdapter<OrderClient, OrdersClientAdapter.OrdersViewHolder>(DiffCallBack){

    companion object DiffCallBack: DiffUtil.ItemCallback<OrderClient>(){
        override fun areItemsTheSame(oldItem: OrderClient, newItem: OrderClient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderClient, newItem: OrderClient): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class OrdersViewHolder(
        private val binding: ItemOrderBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(orderClient: OrderClient){
            binding.apply {
                tvPrice.text = binding.root.context.getString(R.string.price_product_text, orderClient.products.sumOf { it.price }.toString())
                tvTotalOrder.text = binding.root.context.getString(R.string.total_products, orderClient.products.size.toString())
                tvDirection.text = orderClient.address.address
                tvDateOrder.text = orderClient.timestamp
                tvNumberOrder.text = binding.root.context.getString(R.string.number_order_client, orderClient.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }
}