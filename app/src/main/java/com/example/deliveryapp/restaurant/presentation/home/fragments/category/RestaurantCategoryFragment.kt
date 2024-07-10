package com.example.deliveryapp.restaurant.presentation.home.fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.ErrorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ImageSelectorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.SuccessDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.CustomTextWatcher
import com.example.deliveryapp.core.presentation.ui.ex.clearFocusFromAllFields
import com.example.deliveryapp.core.presentation.ui.utils.XmlFileProvider
import com.example.deliveryapp.databinding.FragmentRestaurantCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantCategoryFragment : Fragment() {


    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    private  var _binding: FragmentRestaurantCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RestaurantCategoryViewModel>()

    private val intentCameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                val image = XmlFileProvider.getPathFromBitmap(requireContext(), it)
                viewModel.onAction(RestaurantCategoryAction.OnImageCamaraChange(image))
            }
        }

    private val intentGaleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val image = XmlFileProvider.createFileFromUri(requireContext(), it)
                viewModel.onAction(RestaurantCategoryAction.OnImageGalleryChange(image.toString()))
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListernes(onAction = {action ->
            when(action){
                RestaurantCategoryAction.OnTakeImageSelected -> showDialogSelected()
                else ->Unit
            }
            viewModel.onAction(action)
        })
        updateUi()
    }

    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.events.collect{event ->
                    when(event){
                        is RestaurantCategoryEvent.Error -> {
                            showErrorDialog(error = event.error.asString(this@RestaurantCategoryFragment.requireContext()), title = getString(R.string.error_create_category_title))
                        }
                        RestaurantCategoryEvent.OnSuccess -> {
                            clearForm()
                            showSuccessDialog()
                        }
                    }
                }
            }
        }
    }

    private fun initListernes(
        onAction: (RestaurantCategoryAction) -> Unit
    ) {
        binding.tieNameCategory.addTextChangedListener(CustomTextWatcher{text ->
            onAction(RestaurantCategoryAction.OnNameCategoryChange(text))
        })
        binding.btnSave.setOnClickListener {
            clearFocusFromAllFields()
            onAction(RestaurantCategoryAction.OnCreateCategoryClick)
        }
        binding.ivCategory.setOnClickListener {
            onAction(RestaurantCategoryAction.OnTakeImageSelected)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    if (it.imagePreview.isNotEmpty()) showImage(it.imagePreview)
                    binding.btnSave.isEnabled = it.categoryName.isNotEmpty()
                    binding.pgLoading.isVisible = it.isLoading
                    binding.tvAddIcon.root.isVisible = it.imagePreview.isEmpty()
                }
            }
        }
    }

    private fun clearForm(){
        binding.tieNameCategory.setText("")
        Glide.with(requireContext()).load(R.drawable.ic_image_add).into(binding.ivCategory)
    }

    //Funcion que limpia el foco de los campos de texto
    private fun clearFocusFromAllFields() {
        binding.root.clearFocusFromAllFields(
            binding.tieNameCategory
        )
    }

    //Dialogo que se muestra la creacion de la cuenta fue exitosa
    private fun showSuccessDialog() {
        SuccessDialog.create(
            title = getString(R.string.success_create_category),
            description = getString(R.string.success_create_category_description),
            positiveAction = SuccessDialog.Action(getString(R.string.text_button_init)) {
                it.dismiss()
            }
        ).show(dialogLauncher, requireActivity())
    }

    //Dialogo que se muestra para selecionar el medio por el cual desea obtener la imagen
    private fun showDialogSelected() {
        ImageSelectorDialog.create(
            title = getString(R.string.select_option_for_photo_profile),
            positiveAction = ImageSelectorDialog.Action(getString(R.string.button_text_galery)) {
                it.dismiss()
                intentGaleryLauncher.launch("image/*")
            },
            negativeAction = ImageSelectorDialog.Action(getString(R.string.text_button_camara)) {
                it.dismiss()
                intentCameraLauncher.launch()

            }
        ).show(dialogLauncher, requireActivity())
    }

    //Dialogo que se muestra cuando hay un error en el registro
    private fun showErrorDialog(
        error: String,
        title: String
    ) {
        ErrorDialog.create(
            title = title,
            description = error,
            negativeAction = ErrorDialog.Action(getString(R.string.cancelar_button_text)) {
                it.dismiss()
            },
            positiveAction = ErrorDialog.Action(getString(R.string.retry_login)) {
                viewModel.onAction(RestaurantCategoryAction.OnCreateCategoryClick)
                it.dismiss()
            }
        ).show(dialogLauncher, requireActivity())
    }


    private fun showImage(imageUrl: String) {
        Glide.with(requireContext()).load(imageUrl).into(binding.ivCategory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}