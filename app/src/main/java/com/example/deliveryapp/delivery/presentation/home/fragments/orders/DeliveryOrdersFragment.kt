package com.example.deliveryapp.delivery.presentation.home.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentDeliveryOrdersBinding
import com.example.deliveryapp.delivery.presentation.home.fragments.orders.adapter.TabsPagerDeliveryOrdersAdapter
import com.example.deliveryapp.restaurant.presentation.home.fragments.home.adapter.TabsPagerRestaurantAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DeliveryOrdersFragment : Fragment() {

    private var _binding: FragmentDeliveryOrdersBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }
    private fun initUi() {
        initViewPager()
    }

    private fun initViewPager() {
        binding.tabOrders.apply {
            setSelectedTabIndicatorColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.md_theme_onPrimary
                )
            )
            tabTextColors = ContextCompat.getColorStateList(requireContext(), R.color.md_theme_onPrimary)
            isInlineLabel = true
            tabGravity = TabLayout.GRAVITY_CENTER
            tabMode = TabLayout.MODE_SCROLLABLE
        }
        binding.viewPager.apply {
            adapter = TabsPagerDeliveryOrdersAdapter(childFragmentManager, lifecycle, 3)
            isUserInputEnabled = true
        }
        TabLayoutMediator(binding.tabOrders, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Despachado"
                1 -> "En Camino"
                2 -> "Entregado"
                else -> null
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryOrdersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}