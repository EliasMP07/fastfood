package com.example.deliveryapp.delivery.presentation.mapDelivery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.deliveryapp.R
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.ui.JsonUtil
import com.example.deliveryapp.core.presentation.ui.utils.PermissionHandler
import com.example.deliveryapp.databinding.ActivityDeliveryMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var locationDelivery: LatLng

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityDeliveryMapBinding
    private lateinit var permissionHandler: PermissionHandler
    private lateinit var order: Order
    private val viewModel: DeliveryMapViewModel by viewModels()
    private var marketPosition: Marker? = null
    private var marketClientPosition: Marker? = null
    private val wayPoints = arrayListOf<LatLng>()
    private val WAY_POINT_TAG = "way_point_tag"
    private var noEntry: Boolean = true
    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    companion object {
        fun create(context: Context): Intent = Intent(context, DeliveryMapActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionHandler = PermissionHandler(this, dialogLauncher)
        order = JsonUtil.deserialize(intent.extras?.getString("order") ?: "", Order::class.java)
        initMap()
        setupPermissions()
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
            }
        )
    }

    private fun initUi() {
        // Initialize any additional UI if necessary
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapDelivery) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }


    private fun addDeliveryMarker(latLng: LatLng) {
        marketPosition = marketPosition ?: map.addMarker(
            MarkerOptions().position(latLng).title("Mi posición")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_deliverymark))
        )?.apply { position = latLng }
    }

    private fun addClientMarker(order: Order) {
        marketClientPosition = marketClientPosition ?: map.addMarker(
            MarkerOptions().position(LatLng(order.lat.toDouble(), order.lng.toDouble()))
                .title("Lugar de entrega")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_marketclient))
        )
    }

}
