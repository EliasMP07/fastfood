package com.example.deliveryapp.client.presentation.cartShopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.presentation.address.list.ClientMyAddressActivity
import com.example.deliveryapp.client.presentation.cartShopping.adapter.CartAdapter
import com.example.deliveryapp.core.presentation.ui.UtilsMessage
import com.example.deliveryapp.databinding.ActivityClientCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientCartActivity : AppCompatActivity() {

    private val viewModel: ClientCartViewModel by viewModels()
    private lateinit var binding: ActivityClientCartBinding

    companion object {
        fun create(context: Context): Intent = Intent(context, ClientCartActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initUiState()
        initList()
        initListernes( onAction = {action->
            when(action){
                ClientCartAction.OnBackClick -> onBackPressedDispatcher.onBackPressed()
                ClientCartAction.OnContinueShopClick -> goToMyAddress()
                else -> Unit
            }

        })
        updateUi()
    }

    private fun goToMyAddress(){
        startActivity(ClientMyAddressActivity.create(this))
    }
    private fun initListernes(
        onAction:(ClientCartAction) -> Unit
    ) {
        binding.ivBack.setOnClickListener {
            onAction(ClientCartAction.OnBackClick)
        }
        binding.btnContinue.setOnClickListener {
            onAction(ClientCartAction.OnContinueShopClick)
        }
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.events.collect{event ->
                    when(event){
                        is ClientCartEvent.Error -> {
                            UtilsMessage.showSnackBac(mensaje = event.error.asString(this@ClientCartActivity), this@ClientCartActivity.binding.root)
                        }
                        is ClientCartEvent.Success -> {
                            UtilsMessage.showSnackBac(mensaje = event.message.asString(this@ClientCartActivity), this@ClientCartActivity.binding.root)
                        }
                    }
                }
            }
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    (binding.rvProductsCart.adapter as CartAdapter).submitList(it.productCart)
                    binding.btnContinue.isEnabled = it.productCart.isNotEmpty()
                    binding.tvTotalAllCart.text = getString(R.string.price_product_text, totalPrice(it.productCart).toString())
                    binding.viewEmptyCart.root.isVisible = it.productCart.isEmpty()
                }
            }
        }
    }

    private fun totalPrice(listProducts: List<Product>): Double{
        return listProducts.sumOf { it.price * it.quantity}
    }


    override fun onPause() {
        viewModel.onAction(ClientCartAction.OnUpdateAllCart)
        super.onPause()
    }


    private fun initList() {
        binding.rvProductsCart.apply {
            layoutManager = LinearLayoutManager(this@ClientCartActivity)
            adapter = CartAdapter(
                onRemoveProduct = {product ->
                    viewModel.onAction(ClientCartAction.OnRemoveProductInCartClick(product))
                },
                addQuantityProduct = {
                    viewModel.onAction(ClientCartAction.OnAddQuantityCLick(it))

                },
                removeQuantityProduct = {
                    viewModel.onAction(ClientCartAction.OnRemoveQuantityCLick(it))
                }
            )
        }
    }

}