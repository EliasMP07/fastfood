package com.example.deliveryapp.restaurant.presentation.home.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ItemClientOrderBinding
import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant

class OrdersClientsAdapter(
    private val onOrderSelected : (OrderRestaurant) -> Unit
) : ListAdapter<OrderRestaurant, OrdersClientsAdapter.OrdersClientsViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<OrderRestaurant>() {
        override fun areItemsTheSame(oldItem: OrderRestaurant, newItem: OrderRestaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderRestaurant,
            newItem: OrderRestaurant
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class OrdersClientsViewHolder(
        private val binding: ItemClientOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderRestaurant: OrderRestaurant, onOrderSelected : (OrderRestaurant) -> Unit) {
            binding.apply {
                tvNumberOrder.text =
                    binding.root.context.getString(R.string.number_order_client, orderRestaurant.id)
                tvClient.text = orderRestaurant.client.name
                tvDateOrder.text = orderRestaurant.timestamp
                tvTotalOrder.text = binding.root.context.getString(
                    R.string.total_products,
                    orderRestaurant.productsClient.size.toString()
                )
                tvPrice.text = binding.root.context.getString(
                    R.string.price_product_text,
                    orderRestaurant.productsClient.sumOf { it.price * it.quantity }.toString()
                )
                tvDirection.text = orderRestaurant.address.address
                tvDelivery.text = binding.root.context.getString(
                    R.string.delivery_assing,
                    orderRestaurant.delivery.name
                )
                cvOrdersClients.setOnClickListener {
                    onOrderSelected(orderRestaurant)
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