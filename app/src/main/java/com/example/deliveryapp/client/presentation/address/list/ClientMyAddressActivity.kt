package com.example.deliveryapp.client.presentation.address.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.client.presentation.address.create.ClientCreateAddressActivity
import com.example.deliveryapp.client.presentation.address.list.adapter.AddressAdapter
import com.example.deliveryapp.databinding.ActivityClientMyAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientMyAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientMyAddressBinding
    private lateinit var viewModel: ClientMyAddressViewModel

    private val goToCreateAddress =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.getMyAddress()
            }
        }

    companion object {
        fun create(context: Context): Intent = Intent(context, ClientMyAddressActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientMyAddressBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ClientMyAddressViewModel::class.java]
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initListeners()
        initUiState()
        initList()
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    val adapter = binding.rvMysDirections.adapter as AddressAdapter
                    adapter.submitList(state.myAddress)
                    adapter.updateSelectedAddress(state.selectedAddressId)
                    binding.btnContinue.isEnabled = !state.selectedAddressId.isNullOrEmpty()
                }
            }
        }
    }

    private fun initList() {
        binding.rvMysDirections.apply {
            layoutManager = LinearLayoutManager(this@ClientMyAddressActivity, LinearLayoutManager.VERTICAL, false)
            adapter = AddressAdapter(
                onAddressSelect = { address ->
                    viewModel.selectAddress(address.id)
                }
            )
        }
    }

    private fun initListeners() {
        binding.fabAddAddress.setOnClickListener {
            goToCreateAddress.launch(ClientCreateAddressActivity.create(this))
        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}