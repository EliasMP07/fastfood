package com.example.deliveryapp.client.presentation.products

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.model.CategorySerializable
import com.example.deliveryapp.client.presentation.home.fragments.profile.convertStringToObject
import com.example.deliveryapp.client.presentation.home.fragments.profile.passObjectToString
import com.example.deliveryapp.client.presentation.productDetail.DetailProductActivity
import com.example.deliveryapp.client.presentation.products.adapters.ProductAdapter
import com.example.deliveryapp.client.domain.mapper.toCategory
import com.example.deliveryapp.client.domain.mapper.toProductUiModel
import com.example.deliveryapp.databinding.ActivityClientProductListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientProductListActivity : AppCompatActivity() {

    private val viewModel: ClientProductListViewModel by viewModels()
    private val args: ClientProductListActivityArgs by navArgs()


    private lateinit var binding: ActivityClientProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val category = convertStringToObject<CategorySerializable>(args.category).toCategory()
        viewModel.getProducts(category)
        initUi()
    }

    private fun initUi() {
        initUiState()
        initList()
    }

    private fun initList() {
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(this@ClientProductListActivity, 2)
            adapter = ProductAdapter(
                onProductSelected = {
                    goToDetailProduct(it)
                }
            )
        }
    }

    private fun goToDetailProduct(product: Product){
        startActivity(DetailProductActivity.create(this).apply {
            putExtra("product", passObjectToString(product.toProductUiModel()))
        })
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.titleToolBar.text = it.category.name
                    (binding.rvProducts.adapter as ProductAdapter).submitList(it.listProducts)
                }
            }
        }
    }


}