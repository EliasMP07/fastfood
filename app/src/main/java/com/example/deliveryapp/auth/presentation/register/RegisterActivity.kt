package com.example.deliveryapp.auth.presentation.register

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.client.presentation.home.ClientHomeActivity
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.ErrorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ImageSelectorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.SuccessDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.ex.clearFocusFromAllFields
import com.example.deliveryapp.core.presentation.ui.ex.hideKeyboard
import com.example.deliveryapp.core.presentation.ui.startActivityWithFinish
import com.example.deliveryapp.core.presentation.ui.utils.PermissionHandler
import com.example.deliveryapp.core.presentation.ui.utils.XmlFileProvider.Companion.createFileFromUri
import com.example.deliveryapp.core.presentation.ui.utils.XmlFileProvider.Companion.getPathFromBitmap
import com.example.deliveryapp.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var permissionHandler: PermissionHandler

    private val viewModel: RegisterViewModel by viewModels()

    companion object {
        fun create(context: Context): Intent =
            Intent(context, RegisterActivity::class.java)
    }

    private val intentCameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                val image = getPathFromBitmap(this@RegisterActivity, it)
                viewModel.onAction(RegisterAction.OnImageCamaraChange(image))
            }
        }

    private val intentGaleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val image = createFileFromUri(this@RegisterActivity, it)
                viewModel.onAction(RegisterAction.OnImageGalleryChange(image.toString()))
            }
        }

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionHandler = PermissionHandler(this, dialogLauncher)
        initUi()
    }


    private fun initUi() {
        initListeners(
            onAction = { action ->
                when (action) {
                    RegisterAction.OnLoginClick -> goToLogin()
                    RegisterAction.OnTakePhotoSelected -> showDialogSelected()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        initUiState()
        updateUi()
    }

    //Funcion que escucha y actualiza la Ui dependiendo de los eventos
    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvent.Error -> {
                            showErrorDialog(
                                error = event.error.asString(this@RegisterActivity),
                                title = getString(R.string.error)
                            )
                        }
                        RegisterEvent.Success -> {
                            showSuccessDialog()
                        }
                    }

                }
            }
        }
    }

    fun validatePermission() {
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
            title = getString(R.string.success_create_account),
            description = getString(R.string.success_create_account_description),
            positiveAction = SuccessDialog.Action(getString(R.string.text_button_init)) {
                it.dismiss()
                goToHome()
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
                viewModel.onAction(RegisterAction.OnRegisterClick)
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }

    /**
     * Funcion que contiene y escucha las acciones de los campos de texto y botones.
     *
     * @param onAction Lambda que recibe las acciones de los botones y campos de texto
     */
    private fun initListeners(
        onAction: (RegisterAction) -> Unit
    ) {
        binding.tieEmail.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnEmailChange(text.toString()))
        }
        binding.tiePassword.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnPasswordChange(text.toString()))
        }
        binding.tieName.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnNameChange(text.toString()))
        }
        binding.tieLastName.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnLastNameChange(text.toString()))
        }
        binding.tiePhoneNumber.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnPhoneNumberChange(text.toString()))
        }
        binding.btnRegister.setOnClickListener {
            clearFocusFromAllFields()
            onAction(RegisterAction.OnRegisterClick)
        }
        binding.tvLogin.setOnClickListener {
            onAction(RegisterAction.OnLoginClick)
        }
        binding.ivProfile.setOnClickListener {
            onAction(RegisterAction.OnTakePhotoSelected)
        }
        binding.tiePassword.setOnEditorActionListener { v, actionId, event ->
            when(actionId){
                EditorInfo.IME_ACTION_DONE ->{
                    this@RegisterActivity.hideKeyboard()
                    clearFocusFromAllFields()
                    true
                }
                else ->  false
            }
        }
    }

    //Funcion que limpia el foco de los campos de texto
    private fun clearFocusFromAllFields() {
        binding.root.clearFocusFromAllFields(
            binding.tieEmail,
            binding.tiePassword,
            binding.tieName,
            binding.tieLastName,
            binding.tiePhoneNumber
        )
    }

    //Funcion que observa los cambios de los estados de la Ui
    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    with(binding) {
                        btnRegister.isEnabled = it.canRegister
                        pgLoading.isVisible = it.isLoading
                        hasNumber.isEnabled = it.passwordValidationState.hasNumber
                        hasMinLength.isEnabled = it.passwordValidationState.hasMinLength
                        hasLowerCase.isEnabled = it.passwordValidationState.hasLowerCaseCharacter
                        hasUpperCase.isEnabled = it.passwordValidationState.hasUpperCaseCharacter
                        if (it.imagePreview.isNotEmpty()) showImage(it.imagePreview)
                    }
                }
            }
        }
    }

    private fun showImage(imageUrl: String) {
        Glide.with(this@RegisterActivity).load(imageUrl).into(binding.ivProfile)
    }

    private fun takePhoto() {
        intentCameraLauncher.launch()
    }

    //Funcion que dirige a la pantalla de Home
    private fun goToHome() {
        startActivityWithFinish(ClientHomeActivity.create(this))
    }

    //Funcion que dirige a la pantalla de Login
    private fun goToLogin() {
        startActivityWithFinish(LoginActivity.create(this))
    }

}
