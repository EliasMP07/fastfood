package com.example.deliveryapp.restaurant.presentation.home.fragments.home.order

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.client.presentation.address.create.ClientCreateAddressAction
import com.example.deliveryapp.client.presentation.address.models.AddressInfoSerializable
import com.example.deliveryapp.client.presentation.address.models.toAddressInfo
import com.example.deliveryapp.client.presentation.home.fragments.profile.convertStringToObject
import com.example.deliveryapp.core.presentation.ui.JsonUtil
import com.example.deliveryapp.databinding.FragmentClientOrderStatusBinding
import com.example.deliveryapp.databinding.FragmentRestaurantStatusOrderBinding
import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant
import com.example.deliveryapp.restaurant.domain.model.ProductClient
import com.example.deliveryapp.restaurant.presentation.home.fragments.home.adapter.OrdersClientsAdapter
import com.example.deliveryapp.restaurant.presentation.home.fragments.home.orderDetail.DetailOrderClientActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantStatusOrdersFragment : Fragment() {

    private lateinit var viewModel: RestaurantStatusOrdersViewModel

    private var _binding: FragmentRestaurantStatusOrderBinding? = null

    private lateinit var status: String

    private val goToDetailOrder = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getOrders(status)
        }
    }

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RestaurantStatusOrdersViewModel::class.java]
        status = arguments?.getString("status")?:""
        viewModel.getOrders(status)
        initUi()
    }

    private fun initUi() {
        initUiState()
        initList()
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest {
                    (binding.rvOrdersClient.adapter  as OrdersClientsAdapter).submitList(it.orders)
                    binding.viewEmptyOrders.root.isVisible = it.orders.isEmpty()
                }
            }
        }
    }

    private fun initList() {
        binding.rvOrdersClient.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = OrdersClientsAdapter(
                onOrderSelected = {
                    goToDetailOrder.launch(DetailOrderClientActivity.create(requireContext()).apply {
                        putExtra("order", JsonUtil.serialize(it, OrderRestaurant::class.java))
                    })
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantStatusOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}