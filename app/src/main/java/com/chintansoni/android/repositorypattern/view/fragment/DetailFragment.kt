package com.chintansoni.android.repositorypattern.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.DetailFragmentBinding
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.view.BundleConstants
import kotlinx.android.synthetic.main.frag_details.*


class DetailFragment : Fragment() {

    lateinit var mDetailFragmentBinding: DetailFragmentBinding

    companion object {
        fun newInstance(user: User): DetailFragment {
            val detailsFragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(BundleConstants.user, user)
            detailsFragment.arguments = bundle
            return detailsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.frag_details, container, false)

        arguments?.let {
            (it.getParcelable(BundleConstants.user) as? User)?.let {
                mDetailFragmentBinding.user = it
            }
        }
        return mDetailFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ivClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}