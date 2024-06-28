package com.example.deliveryapp.core.presentation.ui.utils


import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.deliveryapp.core.presentation.designsystem.dialog.DeniedPermissionDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.RequirePermissionDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.ex.openAppSettings

import android.content.Context
import android.content.SharedPreferences
import com.example.deliveryapp.R

class PermissionHandler(
    private val activity: AppCompatActivity,
    private val dialogLauncher: DialogFragmentLauncher
) {

    private val sharedPreferences: SharedPreferences = activity.getSharedPreferences("PermissionHandlerPrefs", Context.MODE_PRIVATE)

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var multiPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var onPermissionResult: ((Boolean) -> Unit)? = null
    private var onMultiPermissionResult: ((Map<String, Boolean>) -> Unit)? = null

    init {
        registerPermissionLaunchers()
    }

    private fun registerPermissionLaunchers() {
        permissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                onPermissionResult?.invoke(isGranted)
            }
        multiPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                onMultiPermissionResult?.invoke(result)
            }
    }

    private fun isPermissionRequestedBefore(permission: String): Boolean {
        return sharedPreferences.getBoolean(permission, false)
    }

    private fun setPermissionRequested(permission: String) {
        sharedPreferences.edit().putBoolean(permission, true).apply()
    }

    private fun showPermissionRationale(
        permission: String,
        description: String,
        onGranted: () -> Unit,
    ) {
        RequirePermissionDialog.create(
            title = activity.getString(R.string.title_permission_required),
            description = description,
            positiveAction = RequirePermissionDialog.Action(activity.getString(R.string.set_permission_text_button)) {
                requestPermission(permission, onGranted) { }
                it.dismiss()
            },
            negativeAction = RequirePermissionDialog.Action(activity.getString(R.string.cancell_text_button)) {
                it.dismiss()
            }
        ).show(dialogLauncher, activity)
    }

    private fun showMultiPermissionRationale(
        permissions: Array<String>,
        description: String,
        onGranted: () -> Unit,
    ) {
        RequirePermissionDialog.create(
            title = activity.getString(R.string.title_multipermission_required),
            description = description,
            positiveAction = RequirePermissionDialog.Action(activity.getString(R.string.set_permission_text_button)) {
                requestMultiPermissions(permissions, onGranted, {})
                it.dismiss()
            },
            negativeAction = RequirePermissionDialog.Action(activity.getString(R.string.cancell_text_button)) {
                it.dismiss()
            }
        ).show(dialogLauncher, activity)
    }

    private fun showPermissionDeniedMessage() {
        DeniedPermissionDialog.create(
            title = activity.getString(R.string.permission_denied),
            description = activity.getString(R.string.description_permission_denied),
            positiveAction = DeniedPermissionDialog.Action(activity.getString(R.string.open_settings)) {
                activity.openAppSettings()
                it.dismiss()
            }
        ).show(dialogLauncher, activity)
    }

    fun checkAndRequestPermission(
        permission: String,
        onGranted: () -> Unit,
        description: String,
        automatic: Boolean = true
    ) {
        when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
            }
            activity.shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationale(
                    permission = permission,
                    description = description,
                    onGranted = onGranted,
                )
            }
            !isPermissionRequestedBefore(permission) -> {
                setPermissionRequested(permission)
                if (automatic) {
                    requestPermission(permission, onGranted) {
                        // Verificar si el usuario seleccionó "No volver a preguntar"
                        if (!activity.shouldShowRequestPermissionRationale(permission)) {
                            showPermissionDeniedMessage()
                        }
                    }
                } else {
                    showPermissionRationale(
                        permission = permission,
                        description = description,
                        onGranted = onGranted,
                    )
                }
            }
            else -> {
                if (!activity.shouldShowRequestPermissionRationale(permission)) {
                    showPermissionDeniedMessage()
                } else {
                    showPermissionRationale(
                        permission = permission,
                        description = description,
                        onGranted = onGranted,
                    )
                }
            }
        }
    }

    fun checkAndRequestMultiPermissions(
        permissions: Array<String>,
        onAllGranted: () -> Unit,
        description: String,
        automatic: Boolean = true
    ) {
        val allPermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allPermissionsGranted) {
            onAllGranted()
        } else {
            val showRationale = permissions.any {
                activity.shouldShowRequestPermissionRationale(it)
            }
            if (showRationale) {
                showMultiPermissionRationale(
                    permissions = permissions,
                    description = description,
                    onGranted = onAllGranted,
                )
            } else {
                val anyPermissionNotRequestedBefore = permissions.any { !isPermissionRequestedBefore(it) }
                if (anyPermissionNotRequestedBefore) {
                    permissions.forEach { setPermissionRequested(it) }
                    if (automatic) {
                        requestMultiPermissions(permissions, onAllGranted) {
                            // Verificar si el usuario seleccionó "No volver a preguntar" para alguno de los permisos
                            val permanentlyDenied = permissions.any { permission ->
                                !activity.shouldShowRequestPermissionRationale(permission) &&
                                        ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
                            }
                            if (permanentlyDenied) {
                                showPermissionDeniedMessage()
                            }
                        }
                    } else {
                        showMultiPermissionRationale(
                            permissions = permissions,
                            description = description,
                            onGranted = onAllGranted,
                        )
                    }
                } else {
                    val permanentlyDenied = permissions.any { permission ->
                        !activity.shouldShowRequestPermissionRationale(permission)
                    }
                    if (permanentlyDenied) {
                        showPermissionDeniedMessage()
                    } else {
                        showMultiPermissionRationale(
                            permissions = permissions,
                            description = description,
                            onGranted = onAllGranted,
                        )
                    }
                }
            }
        }
    }

    private fun requestPermission(permission: String, onGranted: () -> Unit, onDenied: () -> Unit) {
        onPermissionResult = { isGranted ->
            if (isGranted) {
                onGranted()
            } else {
                onDenied()
            }
        }
        permissionLauncher.launch(permission)
    }

    private fun requestMultiPermissions(
        permissions: Array<String>,
        onAllGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        onMultiPermissionResult = { result ->
            val allGranted = result.values.all { it }
            if (allGranted) {
                onAllGranted()
            } else {
                onDenied()
            }
        }
        multiPermissionLauncher.launch(permissions)
    }
}