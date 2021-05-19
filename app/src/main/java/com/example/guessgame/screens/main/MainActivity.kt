package com.example.guessgame.screens.main

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.guessgame.R
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject<MainViewModel>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel
            .isLoggedIn()
            ?.subscribe { isLoggedIn, t2 ->
                if (isLoggedIn) {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.GameFragment)
                }
            }?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    override fun onDestroy() {
        viewModel.dispose()
        compositeDisposable.clear()
        super.onDestroy()
    }

}