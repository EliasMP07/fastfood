package com.example.deliveryapp.core.presentation.ui

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.deliveryapp.DeliveryApp
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

object UtilsMessage {

    fun showAlertOk(titulo: String?, mensaje: String?, contexto: Context) {
        val builder = MaterialAlertDialogBuilder(contexto)
        builder.setMessage(mensaje)
            .setTitle(titulo)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    fun showToast(mensaje: String) {
        Toast.makeText(
            DeliveryApp.getContext(),
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }

    fun showSnackBac(mensaje: String, view: View){
        Snackbar.make(
            view,
            mensaje,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}