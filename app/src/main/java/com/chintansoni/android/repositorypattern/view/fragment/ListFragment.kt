package com.chintansoni.android.repositorypattern.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.ListFragmentBinding
import com.chintansoni.android.repositorypattern.model.Resource
import com.chintansoni.android.repositorypattern.util.setInfiniteScroll
import com.chintansoni.android.repositorypattern.view.adapter.UserRecyclerAdapter
import com.chintansoni.android.repositorypattern.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    lateinit var mFragmentBinding: ListFragmentBinding

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel by viewModel<ListViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        return mFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        listenToViewModel()
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModel.getUsers()
    }

    private fun listenToViewModel() {
        viewModel.userLiveData.observe(this, Observer { resource ->
            srl_list.isRefreshing = false
            resource?.let {
                when (it) {
                    is Resource.Success -> {
                        userRecyclerAdapter.removeLoader()
                        userRecyclerAdapter.setList(ArrayList(it.data))
                    }
                    is Resource.Error ->
                        userRecyclerAdapter.removeLoader()
                    is Resource.Loading ->
                        userRecyclerAdapter.addLoader()
                }
            }
        })
    }

    private lateinit var userRecyclerAdapter: UserRecyclerAdapter

    private fun initViews() {
        userRecyclerAdapter = UserRecyclerAdapter(requireContext())
        rvUsers.adapter = userRecyclerAdapter

        val animator = rvUsers.itemAnimator
        if (animator is androidx.recyclerview.widget.SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        rvUsers.setInfiniteScroll {
            if (!userRecyclerAdapter.isLoading()) {
                post {
                    userRecyclerAdapter.addLoader()
                }
                viewModel.getNextPageUsers()
            }
        }

        srl_list.setOnRefreshListener {
            viewModel.refreshUsers()
        }
    }
}
