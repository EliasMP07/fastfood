package com.example.deliveryapp.client.presentation.address.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deliveryapp.R
import com.example.deliveryapp.client.presentation.address.create.ClientCreateAddressActivity
import com.example.deliveryapp.client.domain.model.AddressInfo
import com.example.deliveryapp.core.domain.model.Location
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.ui.JsonUtil
import com.example.deliveryapp.core.presentation.ui.utils.PermissionHandler
import com.example.deliveryapp.databinding.ActivityMapGoogleBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapGoogleActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private lateinit var binding: ActivityMapGoogleBinding

    private lateinit var permissionHandler: PermissionHandler

    private val viewModel: MapGoogleViewModel by viewModels()

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    companion object {
        fun create(context: Context): Intent = Intent(context, MapGoogleActivity::class.java)
    }

    private var isUserInteractingWithMap = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionHandler = PermissionHandler(this, dialogLauncher)
        initMap()
        setupPermissions()
    }

    private fun onPointChange(){
        setResult(RESULT_OK, ClientCreateAddressActivity.create(this).apply {
            putExtra("pointReference", JsonUtil.serialize(viewModel.state.value.addressInfo!!, AddressInfo::class.java))
        })
        finish()
    }


    private fun setupPermissions() {
        permissionHandler.checkAndRequestMultiPermissions(
            permissions = arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            description = "",
            automatic = true,
            onAllGranted = {
                initUi()
            }
        )
    }

    private fun initUi() {
        initListernes()
        observeViewModel()
    }

    private fun initListernes() {
        binding.btnSelectSave.setOnClickListener {
            onPointChange()
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setupMapListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.tvLocationPosition.text = state.addressInfo?.address ?: ""
                    if (this@MapGoogleActivity::map.isInitialized) {
                        state.currentLocation?.let { location ->
                            val latLng = LatLng(location.lat, location.long)
                            viewModel.insertPosition(latLng)
                            map.moveCamera(
                                CameraUpdateFactory.newCameraPosition(
                                    CameraPosition.builder().target(latLng).zoom(17f).build()
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setupMapListeners() {
        map.setOnCameraMoveStartedListener { reason ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                isUserInteractingWithMap = true
            }
        }
        map.setOnCameraIdleListener {
            if (isUserInteractingWithMap) {
                val position = map.cameraPosition.target
                viewModel.onAction(
                    MapGoogleAction.OnDirectionChange(
                        camaraPosition = Location(
                            lat = position.latitude,
                            long = position.longitude
                        )
                    )
                )
                isUserInteractingWithMap = false
            }
        }
    }
}