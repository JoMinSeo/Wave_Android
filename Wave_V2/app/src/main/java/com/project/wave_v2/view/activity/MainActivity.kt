package com.project.wave_v2.view.activity

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.project.wave_v2.R
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val KEY_USER = "user_info"
    var viewModel : SearchedViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.fragment_host)
        viewModel = ViewModelProvider(this).get(SearchedViewModel::class.java)
        val dockView : ConstraintLayout = findViewById(R.id.playingDock)
        val animRiseUp: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.riseup)
        val animFallDown = AnimationUtils.loadAnimation(applicationContext, R.anim.dropdown)

        viewModel!!.isViewing!!.observe(this,
            Observer<Boolean> {
                if (viewModel!!.isViewing!!.value!!) {
                    dockView.startAnimation(animRiseUp)
                    dockView.visibility = View.VISIBLE
                } else {
                    dockView.startAnimation(animFallDown)
                    dockView.visibility = View.GONE
                }
            })

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

}