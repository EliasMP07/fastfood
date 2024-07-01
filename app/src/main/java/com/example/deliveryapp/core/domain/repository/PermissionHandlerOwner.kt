package com.example.deliveryapp.core.domain.repository

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

interface PermissionHandlerOwner {
    val context: Context
    fun registerForActivityResult(contract: ActivityResultContracts.RequestPermission, callback: (Boolean) -> Unit): ActivityResultLauncher<String>
    fun registerForActivityResult(contract: ActivityResultContracts.RequestMultiplePermissions, callback: (Map<String, Boolean>) -> Unit): ActivityResultLauncher<Array<String>>
    fun shouldShowRequestPermissionRationale(permission: String): Boolean
}
