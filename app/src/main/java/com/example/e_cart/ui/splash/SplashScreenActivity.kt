package com.example.e_cart.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.e_cart.R
import com.example.e_cart.ui.login.Google.GoogleSignInActivity
import com.example.e_cart.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()

        val layoutSplash = findViewById<ConstraintLayout>(R.id.splashScreeenLayout)
        val animation= AnimationUtils.loadAnimation(applicationContext,R.anim.rotate)
        val userShared = getPreferences(Context.MODE_PRIVATE)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val intentMain = Intent(this@SplashScreenActivity, MainActivity::class.java)
                val intentLogin = Intent(this@SplashScreenActivity, GoogleSignInActivity::class.java)
                val userEmail = userShared.getString("EMAIL", "email")
                if (userEmail!!.compareTo("email") == 0 || userShared == null) {
                  startActivity(intentLogin)
                    animation?.cancel()
                    finish()
                } else {
                    startActivity(intentMain)
                    finish()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        }
        )
        layoutSplash.startAnimation(animation)
    }

}