package com.example.deliveryapp.client.presentation.home.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategorySelected:(Category) -> Unit
): ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallBack){

    companion object DiffCallBack: DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category, onCategorySelected: (Category) -> Unit){
            Glide.with(binding.root).load(category.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivCategory)
            binding.apply {
                tvNameCategory.text = category.name
                cvCategory.setOnClickListener {
                    onCategorySelected(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(
            getItem(position), onCategorySelected = onCategorySelected
        )
    }
}