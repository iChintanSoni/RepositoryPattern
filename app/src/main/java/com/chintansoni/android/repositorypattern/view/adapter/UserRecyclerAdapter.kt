package com.chintansoni.android.repositorypattern.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.ItemLoadingBinding
import com.chintansoni.android.repositorypattern.databinding.ItemUserBinding
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.response.Dob
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name
import com.chintansoni.android.repositorypattern.model.remote.response.Picture
import com.chintansoni.android.repositorypattern.util.UserDiffUtil
import com.chintansoni.android.repositorypattern.view.viewholder.LoaderViewHolder
import com.chintansoni.android.repositorypattern.view.viewholder.UserViewHolder

class UserRecyclerAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<User> = ArrayList()
    private val ITEM_TYPE_NORMAL = 1
    private val ITEM_TYPE_LOADER = 2
    private var listener: ItemTouchListener

    init {
        listener = context as ItemTouchListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == 0) {
            ITEM_TYPE_LOADER
        } else {
            ITEM_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_NORMAL) {
            val mUserBinding: ItemUserBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.context), R.layout.list_item_user, parent, false)
            UserViewHolder(mUserBinding)
        } else {
            val mLoadingBinding: ItemLoadingBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.context), R.layout.list_item_loader, parent, false)
            LoaderViewHolder(mLoadingBinding)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var clickListener: View.OnClickListener = View.OnClickListener {
            listener.onItemClick(list[holder.adapterPosition])
        }

        when (holder.itemViewType) {
            ITEM_TYPE_NORMAL -> {
                holder as UserViewHolder
                holder.setUser(list[position])
                holder.setClickListener(clickListener)
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
        return User(id = 0, name = Name(), picture = Picture(), location = Location(), email = "", dob = Dob(), cell = "", gender = "")
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

    fun getItem(position: Int): User {
        return list.get(position)
    }

    interface ItemTouchListener {
        fun onItemClick(user: User)
    }
}