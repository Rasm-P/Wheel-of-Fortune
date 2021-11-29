package com.example.wheeloffortune.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingDecorator(private val itemOffset: Int) : RecyclerView.ItemDecoration() {

    //Custom offset for the recyclerview
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = itemOffset
    }
}