package com.example.deliveryapp.delivery.presentation.orderDetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
import com.example.deliveryapp.databinding.ActivityDeliveryDetailOrderBinding
import com.example.deliveryapp.restaurant.presentation.home.restaurantOrderDetail.DetailOrderClientEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryDetailOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeliveryDetailOrderBinding

    private lateinit var order: Order

    private lateinit var viewModel: DeliveryDetailOrderViewModel

    companion object {
        fun create(context: Context): Intent = Intent(
            context,
            DeliveryDetailOrderActivity::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryDetailOrderBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[DeliveryDetailOrderViewModel::class.java]
        order = JsonUtil.deserialize(
            intent.extras?.getString("order") ?: "",
            Order::class.java
        )
        viewModel.getOrder(order)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initUiState()
        initList()
        updateUi()
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest {
                    renderOrder(it.order?:order)
                    renderButton(it.order?:order)
                }
            }
        }
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is DeliveryDetailOrderEvent.Error -> {
                            UtilsMessage.showToast(event.error.asString(this@DeliveryDetailOrderActivity))
                        }
                        is DeliveryDetailOrderEvent.Success -> {
                            UtilsMessage.showToast(event.message.asString(this@DeliveryDetailOrderActivity))
                        }
                    }
                }
            }
        }
    }


    private fun renderButton(order: Order) {
        binding.btnInitDelivery.apply {
            text = if (order.status == "EN CAMINO") "Regresar al mapa" else "Iniciar entrega"
            setOnClickListener {
                when (order.status) {
                    "ENTREGA" -> animateDeliveryStart()
                    "EN CAMINO" -> goToMapDelivery()
                    else -> animateDeliveryStart()
                }
            }
        }
    }

    private fun goToMapDelivery(){
        UtilsMessage.showToast("Regresar al mapa")
    }

    private fun initList() {
        binding.rvProductsOrders.apply {
            layoutManager = LinearLayoutManager(
                this@DeliveryDetailOrderActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ProductsClientAdapter()
        }
    }

    private fun renderOrder(order: Order) {
        (binding.rvProductsOrders.adapter as ProductsClientAdapter).submitList(order.productsClient)
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

    private fun animateDeliveryStart() {
        binding.iconDelivery.isVisible = true

        // Animación para mover el icono
        val moveIcon = ObjectAnimator.ofFloat(
            binding.iconDelivery,
            "translationX",
            1000f
        ).apply {
            addListener(onStart = {
                binding.btnInitDelivery.text = ""
            })
        }

        // Animación para desvanecer el botón
        val fadeOutButton = ObjectAnimator.ofFloat(
            binding.btnInitDelivery,
            "alpha",
            1f,
            0f
        ).apply {
            addListener(onEnd = {
                // Al finalizar el desvanecimiento del botón
                binding.btnInitDelivery.setBackgroundColor(
                    ContextCompat.getColor(
                        this@DeliveryDetailOrderActivity,
                        R.color.md_theme_onPrimary
                    )
                )
                binding.btnInitDelivery.text = "Haz Iniciado La Entrega"
                binding.iconDelivery.isVisible = false
                binding.btnInitDelivery.setTextColor(
                    ContextCompat.getColor(
                        this@DeliveryDetailOrderActivity,
                        R.color.md_theme_primary
                    )
                )
                binding.btnInitDelivery.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
                viewModel.onAction(DeliveryDetailOrderAction.InitDeliveryClick(order))
            })
        }

        // Animación para mostrar el texto de "Haz Iniciado La Entrega"
        val fadeInText = ObjectAnimator.ofFloat(
            binding.btnInitDelivery,
            "alpha",
            0f,
            1f
        )

        // Configurar el AnimatorSet para reproducir las animaciones en secuencia
        val animatorSet = AnimatorSet().apply {
            playSequentially(
                moveIcon,
                fadeOutButton,
                fadeInText
            )
            duration = 3000 // Duración total de la animación en milisegundos (5 segundos en este caso)
        }

        animatorSet.start()
    }

}