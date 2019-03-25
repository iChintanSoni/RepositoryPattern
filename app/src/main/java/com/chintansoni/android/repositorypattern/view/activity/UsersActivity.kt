package com.chintansoni.android.repositorypattern.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.view.adapter.UserRecyclerAdapter
import com.chintansoni.android.repositorypattern.view.fragment.DetailFragment
import com.chintansoni.android.repositorypattern.view.fragment.ListFragment

class UsersActivity : AppCompatActivity(), UserRecyclerAdapter.ItemTouchListener {
    override fun onItemClick(user: User) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_up,
                R.anim.slide_down
            )
            .add(R.id.container, DetailFragment.newInstance(user))
            .addToBackStack(detailFragmentTag)
            .commit()
    }

    companion object {
        const val detailFragmentTag = "details_fragment"
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
