package com.example.deliveryapp.client.presentation.home.fragments.orders.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deliveryapp.client.presentation.home.fragments.orders.ClientOrderStatusFragment

class TabsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val numberOfTabs: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = numberOfTabs

    override fun createFragment(position: Int): Fragment {
        val fragment = ClientOrderStatusFragment()
        fragment.arguments = Bundle().apply {
            putString("status", when (position) {
                0 -> "PAGADO"
                1 -> "DESPACHADO"
                2 -> "ENCAMINO"
                3 -> "ENTREGADO"
                else -> null
            })
        }
        return fragment
    }
}

