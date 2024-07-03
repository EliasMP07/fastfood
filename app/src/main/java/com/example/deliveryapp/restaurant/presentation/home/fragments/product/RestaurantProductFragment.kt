package com.example.deliveryapp.restaurant.presentation.home.fragments.product

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.core.presentation.ui.CustomTextWatcher
import com.example.deliveryapp.core.presentation.ui.utils.XmlFileProvider
import com.example.deliveryapp.databinding.FragmentRestaurantProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantProductFragment : Fragment() {

    private var _binding: FragmentRestaurantProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RestaurantProductViewModel>()

    private val pickImagesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            handleImageSelection(data)
        }
    }

    private lateinit var categoryAdapter: ArrayAdapter<Category>
    private var currentCategories: List<Category> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        setupCategorySpinner()
        initUiState()
        initListeners { action ->
            when (action) {
                RestaurantProductAction.OnUploadImageClick -> openGallery()
                else -> Unit
            }
            viewModel.onAction(action)
        }
        updateUi()
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is RestaurantProductEvent.Error -> {
                            Toast.makeText(requireContext(), event.error.asString(requireContext()), Toast.LENGTH_LONG).show()
                        }
                        is RestaurantProductEvent.Success -> {
                            clearForm()
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun initListeners(onAction: (RestaurantProductAction) -> Unit) {
        binding.btnSelectImage.setOnClickListener {
            onAction(RestaurantProductAction.OnUploadImageClick)
        }
        binding.tieNameProduct.addTextChangedListener(CustomTextWatcher { text ->
            onAction(RestaurantProductAction.OnNameProductChange(text))
        })
        binding.tieDescriptionProduct.addTextChangedListener(CustomTextWatcher { text ->
            onAction(RestaurantProductAction.OnDescriptionProductChange(text))
        })
        binding.tiePrice.addTextChangedListener(CustomTextWatcher { text ->
            onAction(RestaurantProductAction.OnPriceProductChange(text.toDoubleOrNull() ?: 0.0))
        })
        binding.btnCreateProduct.setOnClickListener {
            onAction(RestaurantProductAction.OnCreateProductClick)
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    updateCategorySpinner(state.categories, state.idCategory)
                    binding.pgLoading.isVisible = state.isLoading
                    binding.btnCreateProduct.isEnabled = state.idCategory.isNotEmpty() && state.name.isNotEmpty() && state.image.isNotEmpty()
                }
            }
        }
    }

    private fun setupCategorySpinner() {
        categoryAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, mutableListOf())
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = categoryAdapter

        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = categoryAdapter.getItem(position)
                selectedCategory?.let {
                    viewModel.onAction(RestaurantProductAction.OnCategoryChange(it.id))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateCategorySpinner(categories: List<Category>, selectedCategoryId: String) {
        if (categories != currentCategories) {
            categoryAdapter.clear()
            categoryAdapter.addAll(categories)
            currentCategories = categories
        }

        val selectedIndex = categories.indexOfFirst { it.id == selectedCategoryId }
        if (selectedIndex >= 0 && binding.spCategory.selectedItemPosition != selectedIndex) {
            binding.spCategory.setSelection(selectedIndex)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearForm(){
        with(binding){
            tieNameProduct.setText("")
            tiePrice.setText("")
            tieDescriptionProduct.setText("")
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickImagesLauncher.launch(intent)
    }

    private fun handleImageSelection(data: Intent?) {
        val newImageUris = mutableListOf<String>()
        data?.let {
            val clipData = it.clipData
            val itemCount = clipData?.itemCount ?: 0

            for (i in 0 until minOf(itemCount, 3)) {
                val imageUri = clipData?.getItemAt(i)?.uri
                imageUri?.let { uri ->
                    val imagePath = XmlFileProvider.createFileFromUri(requireContext(), uri)
                    imagePath?.let { path -> newImageUris.add(path.toString()) }
                }
            }
            data.data?.let { uri ->
                val imagePath = XmlFileProvider.createFileFromUri(requireContext(), uri)
                imagePath?.let { path -> newImageUris.add(path.toString()) }
            }
        }
        updateImageViews(newImageUris)
        viewModel.onAction(RestaurantProductAction.OnImageChange(newImageUris))
    }

    private fun updateImageViews(images: List<String>) {
        val imageViews = listOf(binding.ivImageProduct1, binding.ivImageProduct2, binding.ivImageProduct3)

        for (i in images.indices) {
            Glide.with(requireContext())
                .load(images[i])
                .into(imageViews[i])
        }
    }
}
