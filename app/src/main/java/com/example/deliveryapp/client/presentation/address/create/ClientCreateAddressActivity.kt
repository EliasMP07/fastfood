package com.example.deliveryapp.client.presentation.address.create

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deliveryapp.R
import com.example.deliveryapp.client.presentation.address.map.MapGoogleActivity
import com.example.deliveryapp.client.domain.model.AddressInfo
import com.example.deliveryapp.core.presentation.ui.CustomTextWatcher
import com.example.deliveryapp.core.presentation.ui.JsonUtil
import com.example.deliveryapp.core.presentation.ui.UtilsMessage
import com.example.deliveryapp.databinding.ActivityClientCreateAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientCreateAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientCreateAddressBinding

    private val viewModel: ClientCreateAddressViewModel by viewModels()

    private val goToMap =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val getInfo = result.data?.getStringExtra("pointReference") ?: ""
                val addressInfo = JsonUtil.deserialize(getInfo, AddressInfo::class.java)
                viewModel.onAction(ClientCreateAddressAction.OnReferenceMapChange(addressInfo))
            }
        }

    companion object {
        fun create(context: Context): Intent =
            Intent(context, ClientCreateAddressActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientCreateAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        updateUi()
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.events.collectLatest {event ->
                    when(event){
                        is ClientCreateAddressEvent.OnError -> {
                            UtilsMessage.showToast(event.error.asString(this@ClientCreateAddressActivity))
                        }
                        is ClientCreateAddressEvent.OnSuccess -> {
                            UtilsMessage.showToast(event.message.asString(this@ClientCreateAddressActivity))
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun initUi() {
        initUiState()
        initListernes(
            onAction = { action ->
                when (action) {
                    ClientCreateAddressAction.OnMapSelectPointReferenceClick -> {
                        goToMap.launch(MapGoogleActivity.create(this))
                    }
                    ClientCreateAddressAction.OnBackClick -> onBackPressedDispatcher.onBackPressed()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )

    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    binding.tiePointReference.setText(
                        getString(
                            R.string.text_point_references,
                            it.addressInfo.address,
                            it.addressInfo.city
                        )
                    )
                }
            }
        }
    }

    private fun initListernes(
        onAction: (ClientCreateAddressAction) -> Unit
    ) {
        binding.tieColony.addTextChangedListener(CustomTextWatcher{
            onAction(ClientCreateAddressAction.OnNeighborhoodChange(it))
        })
        binding.tieDirection.addTextChangedListener(CustomTextWatcher{
            onAction(ClientCreateAddressAction.OnAddressChange(it))
        })
        binding.tiePointReference.setOnClickListener {
            onAction(ClientCreateAddressAction.OnMapSelectPointReferenceClick)
        }
        binding.btnCreateAddress.setOnClickListener {
            onAction(ClientCreateAddressAction.OnCreateAddressClick)
        }
        binding.toolbar.ivBack.setOnClickListener {
            onAction(ClientCreateAddressAction.OnBackClick)
        }
    }

}