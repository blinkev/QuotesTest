package com.example.quotes.ui_kit

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.quotes.utils.convertDpToPixel

class DividerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        setupColor()
    }

    private fun setupColor() {
        setBackgroundColor(context.getColor(android.R.color.darker_gray))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthFromSpec = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(widthFromSpec, context.convertDpToPixel(0.5f))
    }
}
