package com.chintansoni.android.repositorypattern.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.ListFragmentBinding
import com.chintansoni.android.repositorypattern.model.Status
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
    }

    private lateinit var adapter: UserRecyclerAdapter

    private fun initViews() {
        adapter = UserRecyclerAdapter(requireContext())
        rvUsers.adapter = adapter

        val animator = rvUsers.itemAnimator
        if (animator is androidx.recyclerview.widget.SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
        rvUsers.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!adapter.isLoading()) {
                    val linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager =
                        recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 2) {

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
        getUsers()
    }
}
