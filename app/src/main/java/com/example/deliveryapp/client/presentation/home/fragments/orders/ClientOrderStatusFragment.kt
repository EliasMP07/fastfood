package com.example.deliveryapp.client.presentation.home.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.client.presentation.home.fragments.orders.adapter.OrdersClientAdapter
import com.example.deliveryapp.databinding.FragmentClientOrderStatusBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientOrderStatusFragment : Fragment() {

    private var _binding: FragmentClientOrderStatusBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ClientOrderStatusViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ClientOrderStatusViewModel::class.java]
        val  status = arguments?.getString("status")?:""
        viewModel.getMyOrders(status)
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
                    (binding.rvOrders.adapter  as OrdersClientAdapter).submitList(it.listOrders)
                    binding.viewEmptyOrders.root.isVisible = it.listOrders.isEmpty()
                }
            }
        }
    }

    private fun initList() {
        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter  = OrdersClientAdapter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientOrderStatusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}