package com.example.chattingwithdapplication.pages

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chattingwithdapplication.R
import com.example.chattingwithdapplication.pages.login.LoginActivity
import com.example.chattingwithdapplication.pages.sign_up.SignUpActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var tvsplash: TextView
    private lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
        tvsplash = findViewById(R.id.tv_splash)
        tvsplash.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}