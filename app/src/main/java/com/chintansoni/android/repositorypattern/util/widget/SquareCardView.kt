package com.chintansoni.android.repositorypattern.util.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet

class SquareCardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}