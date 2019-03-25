package com.chintansoni.android.repositorypattern.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.DetailFragmentBinding
import com.chintansoni.android.repositorypattern.model.local.entity.User
import kotlinx.android.synthetic.main.frag_details.*

class DetailFragment : androidx.fragment.app.Fragment() {

    lateinit var mDetailFragmentBinding: DetailFragmentBinding
    private var user: User? = null

    companion object {
        const val ARG_USER: String = "User"
        fun newInstance(user: User): DetailFragment {
            val detailsFragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_USER, user)
            detailsFragment.arguments = bundle
            return detailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            (it.getParcelable(ARG_USER) as? User)?.let {
                user = it
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.frag_details, container, false)
        return mDetailFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user?.let {
            mDetailFragmentBinding.user = it
        }
        ivClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}