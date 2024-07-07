package com.example.deliveryapp.core.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.deliveryapp.R

fun Activity.startActivityWithFinish(
    intent: Intent
){
    startActivity(intent.apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    })
    finish()
}