package com.example.deliveryapp.delivery.presentation.home.fragments.orders

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.presentation.ui.JsonUtil
import com.example.deliveryapp.databinding.FragmentDeliveryPageOrderBinding
import com.example.deliveryapp.delivery.presentation.home.fragments.orders.adapter.OrdersDeliveryAdapter
import com.example.deliveryapp.delivery.presentation.orderDetail.DeliveryDetailOrderActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryPageOrderFragment : Fragment() {

    private var _binding: FragmentDeliveryPageOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DeliveryPageOrderViewModel
    private lateinit var status: String

    private val goToDetailOrder = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

        }
    }
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        viewModel = ViewModelProvider(this)[DeliveryPageOrderViewModel::class.java]
        status = arguments?.getString("status")?:""
        viewModel.getOrderStatus(status = status)
        initUi()
    }

    private fun initUi() {
        initList()
        initUiState()
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collectLatest {
                    (binding.rvOrdersDelivery.adapter as OrdersDeliveryAdapter).submitList(it.orders)
                    binding.viewEmptyOrders.root.isVisible = it.orders.isEmpty()
                    binding.pgLoading.isVisible = it.isLoading
                }
            }
        }
    }

    private fun initList() {
        binding.rvOrdersDelivery.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = OrdersDeliveryAdapter(
                onOrderSelected = {
                    goToDetailOrder.launch(DeliveryDetailOrderActivity.create(requireContext()).apply {
                        putExtra("order", JsonUtil.serialize(it, Order::class.java))
                    })
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeliveryPageOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}