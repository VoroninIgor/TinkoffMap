package com.voronin.tinkoff.presentation.mainactivity

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    private val viewModel: MainActivityViewModel by lazy { viewModelFactory.create(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBindViewModel() = with(viewModel) {
    }
}
