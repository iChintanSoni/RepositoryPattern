package com.chintansoni.android.repositorypattern.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.model.Status
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name
import com.chintansoni.android.repositorypattern.model.remote.response.Picture
import com.chintansoni.android.repositorypattern.util.widget.NetworkImageView
import com.chintansoni.android.repositorypattern.viewmodel.KotlinViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: KotlinViewModelFactory

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private lateinit var adapter: UserRecyclerAdapter

    private fun initViews() {
        adapter = UserRecyclerAdapter()
        rv_users.adapter = adapter

        rv_users.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!adapter.isLoading()) {
                    val linearLayoutManager: LinearLayoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 1) {

                        // add progress bar, the loading footer
                        recyclerView.post {
                            adapter.addLoader()
                        }

                        viewModel.getNextPageUsers()
                    }
                }
            }

        })

        srl_list.setOnRefreshListener {
            getUsers(true)
        }
    }

    private fun getUsers(isForced: Boolean) {
        viewModel.getUsers(isForced).observe(this, Observer {

            srl_list.isRefreshing = false

            if (it != null) {
                if (it.status == Status.LOADING) {
                    adapter.addLoader()
                } else if (it.status == Status.SUCCESS) {
                    adapter.removeLoader()
                    adapter.setList(ArrayList(it.data))
                } else {
                    adapter.removeLoader()
                    if (adapter.itemCount == 0) {
                        Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
        getUsers(true)
    }

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

        fun addLoader() {
            if (!isLoading()) {
                list.add(User(0, Name(), Picture(), Location()))
                notifyItemInserted(list.size - 1)
            }
        }

        fun removeLoader() {
            if (isLoading()) {
                if (!list.isEmpty()) {
                    list.removeAt(list.size - 1)
                    notifyItemRemoved(list.size - 1)
                }
            }
        }

        fun isLoading(): Boolean {
            return list.isEmpty() || list.last().id == 0
        }
    }

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

    class LoaderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    class UserDiffUtil(private val newList: ArrayList<User>? = null, private val oldList: ArrayList<User>? = null) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id
        }

        override fun getOldListSize(): Int {
            return oldList?.size ?: 0
        }

        override fun getNewListSize(): Int {
            return newList?.size ?: 0
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition) === newList?.get(newItemPosition)
        }
    }
}
