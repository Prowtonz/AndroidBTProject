package com.example.roomautomation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Handler

private const val SPLASH_SCREEN_TIME = 3000L

class SplashScreen: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val intent = Intent(this, MainActivity::class.java)
        Handler().postDelayed(Runnable {
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_TIME)
    }
}