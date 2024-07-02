package com.example.deliveryapp.core.presentation.ui.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.deliveryapp.core.presentation.ui.ex.toBase64
import java.io.ByteArrayOutputStream
import java.io.File

fun imageCamara(image: String): String{
    return File(image).readBytes().toBase64()
}

fun reduceImageSize(imagePath: String): String {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(imagePath, options)

    // Calcula el factor de reducción
    val targetWidth = 800  // ancho objetivo
    val scaleFactor = (options.outWidth / targetWidth).coerceAtLeast(1)

    // Decodifica la imagen con el factor de reducción
    options.inJustDecodeBounds = false
    options.inSampleSize = scaleFactor

    val bitmap = BitmapFactory.decodeFile(imagePath, options)
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun reduceImagesSize(imagesPath: List<String>): List<String> {
    val imageReduces : MutableList<String> = mutableListOf()
    imagesPath.forEach {images ->
        imageReduces.add(reduceImageSize(images))
    }
    return imageReduces
}