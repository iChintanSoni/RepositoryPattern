package com.chintansoni.android.repositorypattern.view.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name
import com.chintansoni.android.repositorypattern.model.remote.response.Picture
import com.chintansoni.android.repositorypattern.util.UserDiffUtil
import com.chintansoni.android.repositorypattern.view.viewholder.LoaderViewHolder
import com.chintansoni.android.repositorypattern.view.viewholder.UserViewHolder

class UserRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<User> = ArrayList()
    private val ITEM_TYPE_NORMAL = 1
    private val ITEM_TYPE_LOADER = 2

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == 0) {
            ITEM_TYPE_LOADER
        } else {
            ITEM_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_NORMAL) {
            return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false))
        } else {
            return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_loader, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_TYPE_NORMAL -> {
                holder as UserViewHolder
                holder.setUser(list[position])
            }
            ITEM_TYPE_LOADER -> {
                // Do Nothing
            }
        }
    }

    fun setList(newList: ArrayList<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffUtil(newList, list))
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }

    private fun getLoaderItem(): User {
        return User(0, Name(), Picture(), Location())
    }

    fun addLoader() {
        if (!isLoading()) {
            val newList = ArrayList<User>(list)
            newList.add(getLoaderItem())
            setList(newList)
        }
    }

    fun removeLoader() {
        if (isLoading()) {
            if (!list.isEmpty()) {
                val newList = ArrayList<User>(list)
                newList.remove(getLoaderItem())
                setList(newList)
            }
        }
    }

    fun isLoading(): Boolean {
        return list.isEmpty() || list.last().id == 0
    }
}