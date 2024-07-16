package com.example.deliveryapp.restaurant.presentation.home.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ItemClientOrderBinding
import com.example.deliveryapp.core.domain.model.order.Order

class OrdersClientsAdapter(
    private val onOrderSelected : (Order) -> Unit
) : ListAdapter<Order, OrdersClientsAdapter.OrdersClientsViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class OrdersClientsViewHolder(
        private val binding: ItemClientOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order, onOrderSelected : (Order) -> Unit) {
            binding.apply {
                tvNumberOrder.text =
                    binding.root.context.getString(R.string.number_order_client, order.id)
                tvClient.text = order.client.name
                tvDateOrder.text = order.timestamp
                tvTotalOrder.text = binding.root.context.getString(
                    R.string.total_products,
                    order.productsClient.size.toString()
                )
                tvPrice.text = binding.root.context.getString(
                    R.string.price_product_text,
                    order.productsClient.sumOf { it.price * it.quantity }.toString()
                )
                tvDirection.text = order.address.address
                tvDelivery.text = binding.root.context.getString(
                    R.string.delivery_assing,
                    order.delivery.name
                )
                cvOrdersClients.setOnClickListener {
                    onOrderSelected(order)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersClientsViewHolder {
        return OrdersClientsViewHolder(
            ItemClientOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersClientsViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            onOrderSelected = onOrderSelected
        )
    }

}