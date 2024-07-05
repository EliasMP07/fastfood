package com.example.deliveryapp.client.presentation.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.presentation.home.fragments.profile.convertStringToObject
import com.example.deliveryapp.client.domain.model.ProductSerializable
import com.example.deliveryapp.client.domain.mapper.toProduct
import com.example.deliveryapp.client.presentation.productDetail.dialog.ReviewProduct
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.UtilsMessage
import com.example.deliveryapp.databinding.ActivityDetailProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: DetailProductVieModel by viewModels()

    companion object {
        fun create(context: Context): Intent = Intent(context, DetailProductActivity::class.java)
    }

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras?.getString("product")
        val product = convertStringToObject<ProductSerializable>(bundle.orEmpty()).toProduct()
        viewModel.insertProduct(product)
        renderProduct(product)
        initUi()
    }

    private fun initUi() {
        initListernes(
            onAction = { action ->
                when(action){
                    DetailProductActions.OnReviewProductClick -> dialogReviewProduct()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        initUiState()
        updateUi()
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is DetailProductEvent.Error -> {
                            UtilsMessage.showSnackBac(
                                mensaje = event.error.asString(this@DetailProductActivity),
                                this@DetailProductActivity.binding.root
                            )
                        }

                        is DetailProductEvent.Success -> {
                            UtilsMessage.showSnackBac(
                                mensaje = event.message.asString(this@DetailProductActivity),
                                this@DetailProductActivity.binding.root
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.tvTotalProduct.text = it.product.quantity.toString()
                    binding.btnAddCart.isEnabled = it.product.quantity > 0
                }
            }
        }
    }

    private fun renderProduct(product: Product) {
        val imagesList = ArrayList<SlideModel>()
        imagesList.add(SlideModel(product.image, ScaleTypes.CENTER_CROP))
        imagesList.add(SlideModel(product.image2, ScaleTypes.CENTER_CROP))
        imagesList.add(SlideModel(product.image3, ScaleTypes.CENTER_CROP))
        binding.apply {
            imsImages.setImageList(imagesList)
            tvRating.text = product.ranting.toString()
            tvDescription.text = product.description
            tvNameProduct.text = product.name
            btnPrice.text = getString(R.string.price_product_text, product.price.toString())

        }
    }

    private fun initListernes(
        onAction: (DetailProductActions) -> Unit
    ) {
        binding.btnAdd.setOnClickListener {
            onAction(DetailProductActions.OnAddProductClick)
        }
        binding.btnRemove.setOnClickListener {
            onAction(DetailProductActions.OnRemoveProductClick)
        }
        binding.btnAddCart.setOnClickListener {
            onAction(DetailProductActions.OnAddCartClick)
        }
        binding.tvReviewProduct.setOnClickListener {
            onAction(DetailProductActions.OnReviewProductClick)
        }
    }

    private fun dialogReviewProduct(){
        ReviewProduct.create(
            title = "Cómo calificarías este producto en general",
            positiveAction = ReviewProduct.Action(getString(R.string.text_button_review), onClickListener = {
                viewModel.onAction(DetailProductActions.OnConfirmReviewProductClick)
                it.dismiss()
            }),
            negativeAction = ReviewProduct.Action(getString(R.string.text_button_not_now), onClickListener = {
                it.dismiss()
            }),
            reviewProductAction = ReviewProduct.Action("", setRanting = {
                viewModel.onAction(DetailProductActions.OnReviewProductChange(it.toDouble()))
            })
        ).show(dialogLauncher, this)
    }
}
