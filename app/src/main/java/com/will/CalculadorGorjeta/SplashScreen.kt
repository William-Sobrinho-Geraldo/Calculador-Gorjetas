package com.will.CalculadorGorjeta

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val imageSplashScreen = findViewById<ImageView>(R.id.image_splash_screen)
        val fadeInAnimation = ObjectAnimator.ofFloat(imageSplashScreen, "alpha",0f,1f)
        fadeInAnimation.duration = 1600
        fadeInAnimation.start()

        Handler().postDelayed({
            mostrarMainActiity()
        }, 2000)
    }

    /** ==================================
    FUNÇÃO Para ir para a MainActivity
    ==================================*/
    private fun mostrarMainActiity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}