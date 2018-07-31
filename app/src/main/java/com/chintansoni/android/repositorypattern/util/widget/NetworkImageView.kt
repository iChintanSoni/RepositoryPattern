package com.chintansoni.android.repositorypattern.util.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chintansoni.android.repositorypattern.util.GlideApp

class NetworkImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    fun setUrl(url: String) {
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
    }
}