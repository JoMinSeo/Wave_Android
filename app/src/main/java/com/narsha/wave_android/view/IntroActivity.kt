package com.narsha.wave_android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.narsha.wave_android.R
import com.narsha.wave_android.view.activity.login.LoginActivity

class IntroActivity : AppCompatActivity() {

    private val INTRO_TIME_OUT:Long = 1300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
        },INTRO_TIME_OUT)
    }
}