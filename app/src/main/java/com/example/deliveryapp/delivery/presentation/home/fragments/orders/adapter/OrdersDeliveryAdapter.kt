package com.example.deliveryapp.delivery.presentation.home.fragments.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.databinding.ItemDeliveryOrderBinding

class OrdersDeliveryAdapter(
    private val onOrderSelected : (Order) -> Unit
) : ListAdapter<Order, OrdersDeliveryAdapter.OrdersDeliveryAdapterViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class OrdersDeliveryAdapterViewHolder(
        private val binding: ItemDeliveryOrderBinding
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
                cvOrdersDelivery.setOnClickListener {
                    onOrderSelected(order)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersDeliveryAdapterViewHolder {
        return OrdersDeliveryAdapterViewHolder(
            ItemDeliveryOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersDeliveryAdapterViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            onOrderSelected = onOrderSelected
        )
    }

}