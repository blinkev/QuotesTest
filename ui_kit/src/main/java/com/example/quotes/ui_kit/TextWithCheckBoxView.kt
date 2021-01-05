package com.example.quotes.ui_kit

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_text_with_check_box.view.*

class TextWithCheckBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var text: String
        get() = text_view.text.toString()
        set(value) {
            text_view.text = value
        }

    var isChecked: Boolean
        get() = check_box.isChecked
        set(value) {
            check_box.isChecked = value
        }

    var checkListener: (isChecked: Boolean) -> Unit = {}

    init {
        initLayoutParams()
        inflate(context, R.layout.view_text_with_check_box, this)
        setupCheckBox()
    }

    private fun initLayoutParams() {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupCheckBox() {
        check_box.setOnCheckedChangeListener { _, isChecked ->
            checkListener.invoke(isChecked) }
    }
}
