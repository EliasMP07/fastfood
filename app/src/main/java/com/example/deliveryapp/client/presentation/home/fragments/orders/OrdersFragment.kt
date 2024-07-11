package com.example.deliveryapp.client.presentation.home.fragments.orders

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.deliveryapp.R
import com.example.deliveryapp.client.presentation.home.fragments.orders.adapter.TabsPagerAdapter
import com.example.deliveryapp.databinding.FragmentOrdersBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

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
            adapter = TabsPagerAdapter(requireActivity().supportFragmentManager, lifecycle, 4)
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
}