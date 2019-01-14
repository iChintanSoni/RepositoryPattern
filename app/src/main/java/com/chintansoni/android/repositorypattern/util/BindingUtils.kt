package com.chintansoni.android.repositorypattern.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name

object BindingUtils {
    @JvmStatic
    @BindingAdapter("app:imageUrl", "app:gender")
    fun loadImage(view: AppCompatImageView, url: String, gender: String) {
        GlideApp.with(view.context)
            .load(url)
            .placeholder(
                when (gender) {
                    "male" -> R.drawable.ic_male_placeholder
                    else -> R.drawable.ic_female_placeholder
                }
            )
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