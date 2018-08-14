package com.chintansoni.android.repositorypattern.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name

object BindingUtils {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        GlideApp.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
    }

    @JvmStatic
    fun createFullName(name: Name): String {
        return name.first.capitalize() + " " + name.last.capitalize()
    }

    @JvmStatic
    fun createLocation(location: Location): String {
        return location.city.capitalize() + ", " + location.state.capitalize()
    }

    @JvmStatic
    fun getDate(s: String): String? {
        return s.split("T")[0]
    }
}