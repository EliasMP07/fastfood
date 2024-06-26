package com.example.deliveryapp.core.presentation.designsystem.dialog.ex

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher

fun DialogFragment.show(launcher: DialogFragmentLauncher, activity: FragmentActivity) {
    launcher.show(this, activity)
}