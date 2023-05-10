package com.will.CalculadorGorjeta

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val imageSplashScreen = findViewById<ImageView>(R.id.image_splash_screen)
        val textCalculador = findViewById<TextView>(R.id.text_Calculador)
        val textCalculador2 = findViewById<TextView>(R.id.text_Calculador_2)
        val duration : Long = 1600

        val fadeInAnimation = ObjectAnimator.ofFloat(imageSplashScreen, "alpha",0f,1f)
        fadeInAnimation.duration = duration
        fadeInAnimation.start()

        val fadeInAnimationText1 = ObjectAnimator.ofFloat(textCalculador,"alpha",0f,1f)
        fadeInAnimationText1.duration = duration
        fadeInAnimationText1.start()

        val fadeInAnimationText2 = ObjectAnimator.ofFloat(textCalculador2,"alpha",0f,1f)
        fadeInAnimationText2.duration = duration
        fadeInAnimationText2.start()

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