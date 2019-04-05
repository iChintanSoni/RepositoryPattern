package com.chintansoni.android.repositorypattern.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setInfiniteScroll(func: RecyclerView.() -> Unit) {
    this.adapter?.let {
        this.addOnScrollListener(object : InfiniteScrollListener(it) {
            override fun fetchNext() {
                func.invoke(this@setInfiniteScroll)
            }
        })
    }
}