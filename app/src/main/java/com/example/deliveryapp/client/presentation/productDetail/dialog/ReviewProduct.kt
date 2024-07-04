package com.example.deliveryapp.client.presentation.productDetail.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.deliveryapp.databinding.DialogReviewProductBinding

class ReviewProduct : DialogFragment(){
    private var title: String = ""
    private var isDialogCancelable: Boolean = true
    private var reviewProductAction: Action = Action.Empty
    private var positiveAction: Action = Action.Empty
    private var negativeAction: Action = Action.Empty

    companion object {
        fun create(
            title: String = "",
            isDialogCancelable: Boolean = true,
            reviewProductAction: Action = Action.Empty,
            positiveAction: Action = Action.Empty,
            negativeAction: Action = Action.Empty,
        ): ReviewProduct = ReviewProduct().apply {
            this.title = title
            this.isDialogCancelable = isDialogCancelable
            this.reviewProductAction= reviewProductAction
            this.positiveAction = positiveAction
            this.negativeAction = negativeAction
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        setMargins(rootView, 50, 20, 50, 20)

    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogReviewProductBinding.inflate(requireActivity().layoutInflater)

        binding.tvTitle.text = title
        binding.btnPositive.text = positiveAction.text
        binding.btnNegative.text = negativeAction.text
        binding.rbProduct.setOnRatingBarChangeListener { _, rating, _->
            reviewProductAction.setRanting(rating)
        }
        binding.btnNegative.setOnClickListener { negativeAction.onClickListener(this) }
        binding.btnPositive.setOnClickListener { positiveAction.onClickListener(this) }
        isCancelable = isDialogCancelable

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCancelable(isDialogCancelable)
            .create()
    }



    data class Action(
        val text: String,
        val onClickListener: (ReviewProduct) -> Unit = {},
        val setRanting:(Float) -> Unit = {}
    ) {
        companion object {
            val Empty = Action("", onClickListener = {}, setRanting = {})
        }
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
}