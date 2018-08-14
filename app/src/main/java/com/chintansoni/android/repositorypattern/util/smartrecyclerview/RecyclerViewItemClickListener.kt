package com.chintansoni.android.repositorypattern.util.smartrecyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerViewItemClickListener(var context: Context, var listener: ItemTouchListener) : RecyclerView.OnItemTouchListener {

    var gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }
        })
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        var childView: View = rv?.findChildViewUnder(e?.x!!.toFloat(), e.y.toFloat()) as View
        if (childView != null && listener != null && gestureDetector.onTouchEvent(e)) {
            listener.onItemClick(rv.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    interface ItemTouchListener {
        fun onItemClick(position: Int)
    }

}