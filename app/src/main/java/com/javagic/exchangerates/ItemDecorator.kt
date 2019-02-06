package com.javagic.exchangerates

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.javagic.exchangerates.ExchangeApp.Companion.instance

class ItemDecorator(@DrawableRes val dividerId: Int = 0) : RecyclerView.ItemDecoration() {

    private val divider by lazy { dividerId.takeIf { it != 0 }?.let { ContextCompat.getDrawable(instance, dividerId) } }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (divider != null) {
            val dividerLeft = parent.paddingLeft
            val dividerRight = parent.width - parent.paddingRight
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + (divider?.intrinsicHeight ?: 0)
                divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                divider?.draw(c)
            }
        } else super.onDraw(c, parent, state)
    }
}