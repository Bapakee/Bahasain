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

class EditTextPassword @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && s.toString().length < minLength) {
                    (parent.parent as? TextInputLayout)?.error =
                        context.getString(R.string.password_length_warning)
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

    fun validateConfirmPassword(confirmPasswordField: EditTextPassword) {
        confirmPasswordField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && s.toString() != this@EditTextPassword.text.toString()) {
                    (confirmPasswordField.parent.parent as? TextInputLayout)?.error =
                        context.getString(R.string.password_same_warning)
                } else {
                    (confirmPasswordField.parent.parent as? TextInputLayout)?.apply {
                        error = null
                        isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        private const val minLength = 8
    }
}