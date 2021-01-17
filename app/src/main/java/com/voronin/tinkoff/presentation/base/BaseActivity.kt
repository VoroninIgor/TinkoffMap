package com.voronin.tinkoff.presentation.base

import android.content.Intent
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

    // In your activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
            fragment.childFragmentManager.fragments.forEach {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@BaseActivity, { t -> block.invoke(t) })
    }
}
