package com.example.deliveryapp.restaurant.presentation.home.restaurantOrderDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.presentation.designsystem.adapter.ProductsClientAdapter
import com.example.deliveryapp.core.presentation.ui.JsonUtil
import com.example.deliveryapp.core.presentation.ui.UtilsMessage
import com.example.deliveryapp.databinding.ActivityDetailOrderClientBinding
import com.example.deliveryapp.restaurant.domain.model.DeliveryAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailOrderClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOrderClientBinding

    private lateinit var viewModel: DetailOrderClientViewModel

    private lateinit var order: Order


    private lateinit var deliveriesAdapter: ArrayAdapter<DeliveryAvailable>
    private var currentDeliveries: List<DeliveryAvailable> = emptyList()

    companion object {
        fun create(context: Context): Intent = Intent(
            context,
            DetailOrderClientActivity::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderClientBinding.inflate(layoutInflater)
        setContentView(binding.root) // Colocar antes para asegurar que la vista está lista
        viewModel = ViewModelProvider(this)[DetailOrderClientViewModel::class.java]
        order = JsonUtil.deserialize(
            intent.extras?.getString("order") ?: "",
            Order::class.java
        )
        initUi()
        renderOrder(order)
    }

    private fun initUi() {
        initUiState()
        initList()
        setupDeliveriesSpinner()
        initListerners(
            onAction = { action ->
                viewModel.onAction(action)
            }
        )
        updateUi()
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is DetailOrderClientEvent.Error -> {
                            UtilsMessage.showToast(event.message.asString(this@DetailOrderClientActivity))
                        }
                        is DetailOrderClientEvent.Success -> {
                            UtilsMessage.showToast(event.message.asString(this@DetailOrderClientActivity))
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun initListerners(
        onAction: (DetailOrderClientActions) -> Unit
    ) {
        binding.btnAssignDelivery.setOnClickListener {
            onAction(DetailOrderClientActions.AssignDeliveryAction(order))
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { uiState ->
                    updateCategorySpinner(
                        deliveries = uiState.deliveriesAvailable,
                        uiState.idDelivery
                    )
                }
            }
        }
    }

    private fun setupDeliveriesSpinner() {
        deliveriesAdapter = ArrayAdapter(
            this@DetailOrderClientActivity,
            android.R.layout.simple_spinner_item,
            mutableListOf()
        )
        deliveriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDelivery.adapter = deliveriesAdapter

        binding.spDelivery.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = deliveriesAdapter.getItem(position)
                selectedCategory?.let {
                    viewModel.onAction(DetailOrderClientActions.OnDeliveryChange(selectedCategory.id))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateCategorySpinner(
        deliveries: List<DeliveryAvailable>,
        selectedDeliveryId: String
    ) {
        if (deliveries != currentDeliveries) {
            deliveriesAdapter.clear()
            deliveriesAdapter.addAll(deliveries)
            currentDeliveries = deliveries
        }

        val selectedIndex = deliveries.indexOfFirst { it.id == selectedDeliveryId }
        if (selectedIndex >= 0 && binding.spDelivery.selectedItemPosition != selectedIndex) {
            binding.spDelivery.setSelection(selectedIndex)
        }
    }

    private fun initList() {
        binding.rvProductsOrders.apply {
            layoutManager = LinearLayoutManager(
                this@DetailOrderClientActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ProductsClientAdapter()
        }
    }

    private fun renderOrder(order: Order) {
        currentDeliveries
        (binding.rvProductsOrders.adapter as ProductsClientAdapter).submitList(order.productsClient)
        if (order.idDelivery.isNotEmpty()) {
            binding.btnAssignDelivery.isVisible = false
            binding.spDelivery.isVisible = false
            binding.tvDelivery.isVisible = false
        }
        binding.apply {
            toolbar.titleToolBar.text = getString(
                R.string.number_order_client,
                order.id
            )
            tvClient.text = getString(
                R.string.fullName,
                order.client.name,
                order.client.lastname
            )
            tvDateOrder.text = order.timestamp
            tvTotalOrder.text = order.productsClient.size.toString()
            tvAddress.text = order.address.address
            tvTotalOrder.text = getString(
                R.string.price_product_text,
                order.productsClient.sumOf { it.price * it.quantity }.toString()
            )
        }
    }

}