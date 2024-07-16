package com.example.deliveryapp.delivery.presentation.home.fragments.orders.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deliveryapp.delivery.presentation.home.fragments.orders.DeliveryPageOrderFragment
import com.example.deliveryapp.restaurant.presentation.home.fragments.home.order.RestaurantStatusOrdersFragment

class TabsPagerDeliveryOrdersAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val numberOfTabs: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = numberOfTabs

    override fun createFragment(position: Int): Fragment {
        val fragment = DeliveryPageOrderFragment()
        fragment.arguments = Bundle().apply {
            putString("status", when (position) {
                0 -> "DESPACHADO"
                1 -> "EN CAMINO"
                2 -> "ENTREGADO"
                else -> null
            })
        }
        return fragment
    }
}
