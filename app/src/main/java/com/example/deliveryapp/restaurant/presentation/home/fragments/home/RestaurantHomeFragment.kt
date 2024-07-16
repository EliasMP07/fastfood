package com.example.deliveryapp.restaurant.presentation.home.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.deliveryapp.R
import com.example.deliveryapp.client.presentation.home.fragments.orders.adapter.TabsPagerClientAdapter
import com.example.deliveryapp.databinding.FragmentRestaurantHomeBinding
import com.example.deliveryapp.restaurant.presentation.home.fragments.home.adapter.TabsPagerRestaurantAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantHomeFragment : Fragment() {

    private var _binding: FragmentRestaurantHomeBinding? = null
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
            adapter = TabsPagerRestaurantAdapter(childFragmentManager, lifecycle, 4)
            isUserInputEnabled = true
        }
        TabLayoutMediator(binding.tabOrders, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Pagado"
                1 -> "Despachado"
                2 -> "En Camino"
                3 -> "Entregado"
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
        _binding = FragmentRestaurantHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}