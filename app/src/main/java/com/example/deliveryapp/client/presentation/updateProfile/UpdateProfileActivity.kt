package com.example.deliveryapp.client.presentation.updateProfile

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.core.data.UserSerializable
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.ErrorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ImageSelectorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.SuccessDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.CustomTextWatcher
import com.example.deliveryapp.core.presentation.ui.ex.clearFocusFromAllFields
import com.example.deliveryapp.core.presentation.ui.utils.PermissionHandler
import com.example.deliveryapp.core.presentation.ui.utils.XmlFileProvider
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.databinding.ActivityUpdateProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class UpdateProfileActivity : AppCompatActivity() {


    private lateinit var permissionHandler: PermissionHandler

    private val args: UpdateProfileActivityArgs by navArgs()
    private lateinit var binding: ActivityUpdateProfileBinding
    private val viewModel: UpdateProfileViewModel by viewModels()

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    private val intentCameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                val image = XmlFileProvider.getPathFromBitmap(this@UpdateProfileActivity, it)
                viewModel.onAction(UpdateProfileAction.OnImageCamaraChange(image))
            }
        }

    private fun showImage(image: String) {
        Glide.with(this@UpdateProfileActivity).load(image).into(binding.ivProfile)
    }

    private val intentGaleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val image = XmlFileProvider.createFileFromUri(this@UpdateProfileActivity, it)
                viewModel.onAction(UpdateProfileAction.OnImageGalleryChange(image.toString()))
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionHandler = PermissionHandler(this, dialogLauncher)
        val user = Json.decodeFromString<UserSerializable>(args.user).toUser()
        viewModel.insertUser(user)
        insertInfoUser(user)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListerners(
            onAction = { action ->
                when (action) {
                    UpdateProfileAction.OnBackClick -> onBackPressedDispatcher.onBackPressed()
                    UpdateProfileAction.OnImageProfileSelectedClick -> showDialogSelected()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        updateUi()
    }



    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is UpdateProfileEvent.Error -> {
                            showErrorDialog(
                                error = event.error.asString(this@UpdateProfileActivity),
                                title = getString(R.string.error)
                            )
                        }
                        is UpdateProfileEvent.Success -> {
                            showSuccessDialog()
                            insertInfoUser(event.user)
                        }
                    }

                }
            }
        }
    }


    //Funcion que limpia el foco de los campos de texto
    private fun clearFocusFromAllFields() {
        binding.root.clearFocusFromAllFields(
            binding.tieName,
            binding.tieLastName,
            binding.tiePhone
        )
    }

    private fun initListerners(
        onAction: (UpdateProfileAction) -> Unit
    ) {
        binding.btnUpdate.setOnClickListener {
            clearFocusFromAllFields()
            onAction(UpdateProfileAction.OnUpdateProfileClick)
        }
        binding.ivBack.setOnClickListener {
            onAction(UpdateProfileAction.OnBackClick)
        }
        binding.ivProfile.setOnClickListener {
            onAction(UpdateProfileAction.OnImageProfileSelectedClick)
        }
        binding.tieName.addTextChangedListener(CustomTextWatcher{text ->
            onAction(UpdateProfileAction.OnNameChange(text))
        })
        binding.tieLastName.addTextChangedListener(CustomTextWatcher{text ->
            onAction(UpdateProfileAction.OnLastNameChange(text))
        })
        binding.tiePhone.addTextChangedListener(CustomTextWatcher{text ->
            onAction(UpdateProfileAction.OnPhoneChange(text))
        })

    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.pgLoadingUpdate.isVisible = it.isLoading
                    showImage(it.user.image)
                }
            }
        }
    }

    private fun insertInfoUser(user: User) {
        with(binding) {
            tieName.setText(user.name)
            tieLastName.setText(user.lastname)
            tiePhone.setText(user.phone)
            showImage(user.image)
        }
    }

    private fun validatePermission() {
        permissionHandler.checkAndRequestPermission(
            Manifest.permission.CAMERA,
            onGranted = {
                takePhoto()
            },
            description = getString(R.string.permission_camera_need),
            automatic = false
        )
    }

    //Dialogo que se muestra la creacion de la cuenta fue exitosa
    private fun showSuccessDialog() {
        SuccessDialog.create(
            title = getString(R.string.title_dialog_update_success),
            description = getString(R.string.decription_dialog_update_success),
            positiveAction = SuccessDialog.Action(getString(R.string.text_button_init)) {
                it.dismiss()
            }
        ).show(dialogLauncher, this)
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
                validatePermission()

            }
        ).show(dialogLauncher, this)
    }

    private fun takePhoto() {
        intentCameraLauncher.launch()
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
                viewModel.onAction(UpdateProfileAction.OnUpdateProfileClick)
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }

}