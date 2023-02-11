package com.example.CalculadorGorjeta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toolbar

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        var handler: Boolean = Handler().postDelayed({
            mostrarMainActiity()
        },1500)
    }

    private fun mostrarMainActiity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}