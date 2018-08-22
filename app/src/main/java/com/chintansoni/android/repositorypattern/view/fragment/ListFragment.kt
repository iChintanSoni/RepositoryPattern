package com.chintansoni.android.repositorypattern.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.ListFragmentBinding
import com.chintansoni.android.repositorypattern.model.Status
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.util.smartrecyclerview.RecyclerViewItemClickListener
import com.chintansoni.android.repositorypattern.view.adapter.UserRecyclerAdapter
import com.chintansoni.android.repositorypattern.viewmodel.KotlinViewModelFactory
import com.chintansoni.android.repositorypattern.viewmodel.ListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : DaggerFragment(), RecyclerViewItemClickListener.ItemTouchListener {

    override fun onItemClick(position: Int) {
        callback.onRequestDetailsFragment(adapter.getItem(position))
    }

    @Inject
    lateinit var viewModelFactory: KotlinViewModelFactory

    lateinit var mFragmentBinding: ListFragmentBinding

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var callback: ActivityCallbacks

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        callback = requireContext() as ActivityCallbacks
        return mFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private lateinit var adapter: UserRecyclerAdapter

    private fun initViews() {
        adapter = UserRecyclerAdapter()
        rvUsers.adapter = adapter
        rvUsers.addOnItemTouchListener(RecyclerViewItemClickListener(requireContext(), this))
        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!adapter.isLoading()) {
                    val linearLayoutManager: LinearLayoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 5) {

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
            viewModel.refreshUsers()
        }
    }

    private fun getUsers() {
        viewModel.getUsers().observe(this, Observer {

            srl_list.isRefreshing = false
            if (it != null) {
                if (it.status == Status.LOADING) {
                    adapter.addLoader()
                } else if (it.status == Status.SUCCESS) {
                    adapter.removeLoader()
                    adapter.setList(ArrayList(it.data))
                } else {
                    adapter.removeLoader()
                    Toast.makeText(context, "Could not fetch new feed.", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
        getUsers()
    }

    interface ActivityCallbacks {
        fun onRequestDetailsFragment(user: User)
    }
}
