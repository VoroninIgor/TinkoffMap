package com.voronin.tinkoffmap.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        onBindViewModel()
    }

    /**
     * В этом методе производить подписки на изменения значений в LiveData у ViewModel
     */
    abstract fun onBindViewModel()

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@BaseActivity, { t -> block.invoke(t) })
    }
}
