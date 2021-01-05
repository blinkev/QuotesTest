package com.example.quotes.ui_kit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_three_text_with_right_icon.view.*

class ThreeTextWithRightIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initLayoutParams()
        inflate(context, R.layout.view_three_text_with_right_icon, this)
        readAttrs(attrs)
        setupIcon()
    }

    var text1: String
        get() = text1_view.text.toString()
        set(value) {
            text1_view.text = value
        }

    var text2: String
        get() = text2_view.text.toString()
        set(value) {
            text2_view.text = value
        }

    var text3: String
        get() = text3_view.text.toString()
        set(value) {
            text3_view.text = value
        }

    var icon: Drawable?
        get() = icon_view.drawable
        set(value) {
            icon_view.setImageDrawable(value)
        }

    var iconClickListener: () -> Unit = {}

    private fun initLayoutParams() {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    @SuppressLint("Recycle")
    private fun readAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ThreeTextWithRightIconView,
            0,
            0
        ).apply {
            try {
                // Получение аттрибутов
                text1 = getString(R.styleable.ThreeTextWithRightIconView_text1) ?: ""
                text2 = getString(R.styleable.ThreeTextWithRightIconView_text2) ?: ""
                text3 = getString(R.styleable.ThreeTextWithRightIconView_text3) ?: ""
            } finally {
                recycle()
            }
        }
    }

    private fun setupIcon() {
        icon_view.setOnClickListener { iconClickListener.invoke() }
    }
}
