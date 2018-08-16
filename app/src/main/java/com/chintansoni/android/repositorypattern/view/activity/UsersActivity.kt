package com.chintansoni.android.repositorypattern.view.activity

import android.os.Bundle
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.view.fragment.DetailFragment
import com.chintansoni.android.repositorypattern.view.fragment.ListFragment
import dagger.android.support.DaggerAppCompatActivity

class UsersActivity : DaggerAppCompatActivity(), ListFragment.ActivityCallbacks {

    companion object {
        val detailFragmentTag = "details_fragment"
    }

    override fun onRequestDetailsFragment(user: User) {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, DetailFragment.newInstance(user))
                .addToBackStack(detailFragmentTag)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commit()
        }
    }
}
