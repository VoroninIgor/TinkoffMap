package com.voronin.tinkoffmap.presentation.mainactivity

import android.os.Bundle
import com.voronin.tinkoffmap.R
import com.voronin.tinkoffmap.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    private val viewModel: MainActivityViewModel by lazy { viewModelFactory.create(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBindViewModel() = with(viewModel) {

    }
}