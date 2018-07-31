package com.chintansoni.android.repositorypattern.view.activity

import android.os.Bundle
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.view.fragment.ListFragment
import dagger.android.support.DaggerAppCompatActivity

class UsersActivity : DaggerAppCompatActivity() {

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
