package com.chintansoni.android.repositorypattern.view.viewholder

import android.support.v7.widget.RecyclerView
import com.chintansoni.android.repositorypattern.databinding.ItemUserBinding
import com.chintansoni.android.repositorypattern.model.local.entity.User

class UserViewHolder(var mUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(mUserBinding.root) {

    fun setUser(user: User) {
        mUserBinding.user = user
    }
}