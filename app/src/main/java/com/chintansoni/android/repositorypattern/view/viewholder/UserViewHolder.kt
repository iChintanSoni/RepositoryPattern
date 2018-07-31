package com.chintansoni.android.repositorypattern.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.util.widget.NetworkImageView

class UserViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    private var networkImageView: NetworkImageView? = null
    private var textViewName: TextView? = null
    private var textViewLocation: TextView? = null

    init {
        networkImageView = itemView?.findViewById(R.id.niv_list_item_user)
        textViewName = itemView?.findViewById(R.id.tv_list_item_user_name)
        textViewLocation = itemView?.findViewById(R.id.tv_list_item_user_location)
    }

    fun setUser(user: User) {
        networkImageView?.setUrl(user.picture.large)

        textViewName?.text = user.name.first + " " + user.name.first

        textViewLocation?.text = user.location.city + ", " + user.location.state
    }
}