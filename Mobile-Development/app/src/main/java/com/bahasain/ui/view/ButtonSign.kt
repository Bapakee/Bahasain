package com.bahasain.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.bahasain.R

class ButtonSign @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private var txtColorEnable: Int = 0
    private var txtColorDisable: Int = 0
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable

    init {
        txtColorEnable = ContextCompat.getColor(context, android.R.color.background_light)
        txtColorDisable = ContextCompat.getColor(context, android.R.color.darker_gray)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_sign) as Drawable
        disabledBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_button_disabled_sign) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackground
        setTextColor(if (isEnabled) txtColorEnable else txtColorDisable)
    }
}