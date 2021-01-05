package com.example.quotes.ui_kit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_toolbar.view.*

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initLayoutParams()
        inflate(context, R.layout.view_toolbar, this)
        readAttrs(attrs)
        setupIcons()
    }

    var text: String
        get() = text_view.text.toString()
        set(value) {
            text_view.text = value
        }

    var leftIcon: Drawable?
        get() = left_icon.drawable
        set(value) {
            left_icon.setImageDrawable(value)
        }

    var rightIcon: Drawable?
        get() = right_icon.drawable
        set(value) {
            right_icon.setImageDrawable(value)
        }

    var leftIconClickListener: () -> Unit = {}
    var rightIconClickListener: () -> Unit = {}

    private fun initLayoutParams() {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupIcons() {
        left_icon.setOnClickListener { leftIconClickListener.invoke() }
        right_icon.setOnClickListener { rightIconClickListener.invoke() }
    }

    @SuppressLint("Recycle")
    private fun readAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarView,
            0,
            0
        ).apply {
            try {
                // Получение аттрибутов
                leftIcon = getDrawable(R.styleable.ToolbarView_leftIcon)
                rightIcon = getDrawable(R.styleable.ToolbarView_rightIcon)
                text = getString(R.styleable.ToolbarView_android_text) ?: ""
            } finally {
                recycle()
            }
        }
    }
}
