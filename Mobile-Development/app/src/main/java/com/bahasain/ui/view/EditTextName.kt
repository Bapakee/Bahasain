package com.bahasain.ui.view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.bahasain.R
import com.google.android.material.textfield.TextInputLayout

class EditTextName @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && s.toString().length < 3) {
                    (parent.parent as? TextInputLayout)?.error =
                        context.getString(R.string.name_length_warning)
                } else {
                    (parent.parent as? TextInputLayout)?.apply {
                        error = null
                        isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}