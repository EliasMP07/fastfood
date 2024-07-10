package com.example.deliveryapp.client.presentation.address.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.client.domain.model.Address
import com.example.deliveryapp.databinding.ItemDirectionBinding

class AddressAdapter(
    private val onAddressSelect: (Address) -> Unit
) : ListAdapter<Address, AddressAdapter.AddressViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }

    private var selectedAddressId: String? = null

    fun updateSelectedAddress(newSelectedAddressId: String?) {
        val oldSelectedPosition = currentList.indexOfFirst { it.id == selectedAddressId }
        val newSelectedPosition = currentList.indexOfFirst { it.id == newSelectedAddressId }

        selectedAddressId = newSelectedAddressId

        if (oldSelectedPosition != -1) notifyItemChanged(oldSelectedPosition)
        if (newSelectedPosition != -1) notifyItemChanged(newSelectedPosition)
    }

    inner class AddressViewHolder(
        private val binding: ItemDirectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.apply {
                tvAddress.text = address.address
                tvNeighborhood.text = address.neighborhood
                ivSelectedAddress.isVisible = address.id == selectedAddressId
                cvDirection.setOnClickListener {
                    onAddressSelect(address)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemDirectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = getItem(position)
        holder.bind(address)
    }

}

