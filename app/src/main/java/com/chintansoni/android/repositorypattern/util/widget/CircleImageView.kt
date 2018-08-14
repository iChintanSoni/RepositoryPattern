package com.chintansoni.android.repositorypattern.util.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class CircleImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var radius: Float = 0f
    var centerX: Float = 0f
    var centerY: Float = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        radius = measuredWidth / 2f
        centerX = measuredWidth / 2f
        centerY = measuredHeight / 2f

    }

    override fun onDraw(canvas: Canvas?) {
        var path = Path()
        path.addCircle(centerX, centerY, radius, Path.Direction.CW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }

}