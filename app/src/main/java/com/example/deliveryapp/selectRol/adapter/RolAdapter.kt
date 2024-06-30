package com.example.deliveryapp.selectRol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.deliveryapp.core.user.domain.model.Rol
import com.example.deliveryapp.databinding.ItemRolBinding

class RolAdapter(
    private val onRolSelected:(Rol) -> Unit
): ListAdapter<Rol, RolAdapter.RolViewHolder>(DiffCallBack){

    companion object DiffCallBack: DiffUtil.ItemCallback<Rol>(){
        override fun areItemsTheSame(oldItem: Rol, newItem: Rol): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rol, newItem: Rol): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class RolViewHolder(
        private val binding: ItemRolBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(rol: Rol, onRolSelected: (Rol) -> Unit){
            Glide.with(binding.ivRol)
                .load(rol.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivRol)
            binding.apply {
                tvNameRol.text = rol.name
                cvRol.setOnClickListener {
                    onRolSelected(rol)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolViewHolder {
        return RolViewHolder(
            ItemRolBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RolViewHolder, position: Int) {
       holder.bind(
           getItem(position), onRolSelected = onRolSelected
       )
    }
}