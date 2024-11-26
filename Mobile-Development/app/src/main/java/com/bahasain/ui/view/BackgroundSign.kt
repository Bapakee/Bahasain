package com.bahasain.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class BackgroundSign  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.parseColor("#0F75BC")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val path = Path()
        val width = width.toFloat()
        val height = height.toFloat()

        path.moveTo(0f, 0f)
        path.lineTo(0f, height * 0.70f)
        path.quadTo(width / 2, height * 1f, width, height * 0.40f)
        path.lineTo(width, 0f)
        path.close()

        canvas.drawPath(path, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 300 // ukuran default (300px)
        val desiredHeight = 300 // ukuran default (300px)

        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }
}
