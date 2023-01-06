package com.example.recyclerview.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.recyclerview.databinding.ActivitySplashBinding
import com.example.recyclerview.ui.doctoractivity.DoctorActivity
import com.example.recyclerview.ui.loginactivity.LoginActivity
import com.example.recyclerview.ui.usuarioactivity.LaunchActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.logoSaludMadrid.load("https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/SaludMadrid.svg/1200px-SaludMadrid.svg.png")

        val handler = Handler(Looper.getMainLooper())
        viewModel.handleEvent(SplashEvent.checkActualUser)

        viewModel.uiState.observe(this) { state ->
            state.rol?.let {
                Timber.d("Role: $it")
                when (it) {
                    "usuario" -> {
                        handler.postDelayed({
                            val intent = Intent(this, LaunchActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                    "doctor" -> {
                        handler.postDelayed({
                            val intent = Intent(this, DoctorActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                    else -> {
                        handler.postDelayed({
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                }
            }
        }
    }
}