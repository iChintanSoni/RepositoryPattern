package com.chintansoni.android.repositorypattern.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy

object BindingUtils {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        GlideApp.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
    }
}