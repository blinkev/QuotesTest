package com.example.quotes.ui_kit

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class DividerDecoration : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }

        val divider = DividerView(parent.context).apply {
            measure(
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
        }
        outRect.top = divider.measuredHeight
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        val divider = DividerView(parent.context).apply {
            measure(
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
        }

        for (i in 0 until parent.childCount) {
            if (i != parent.childCount - 1) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop = child.bottom + params.bottomMargin

                divider.layout(0, dividerTop, parent.width, divider.measuredHeight)
                canvas.save()
                canvas.translate(0f, dividerTop.toFloat())

                divider.draw(canvas)
                canvas.restore()
            }
        }
    }

}