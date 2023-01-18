package com.example.recyclerview.ui.splashactivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.recyclerview.databinding.ActivitySplashBinding
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.peliculasactivity.PeliculasActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.logoSaludMadrid.load(ConstantesUI.SPLASH_LOGO_URL)

        val handler = Handler(Looper.getMainLooper())
        viewModel.handleEvent(SplashEvent.CheckActualUser)

        handler.postDelayed({
            val intent = Intent(this, PeliculasActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}