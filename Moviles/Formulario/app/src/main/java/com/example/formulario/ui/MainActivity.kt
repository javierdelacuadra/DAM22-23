package com.example.formulario.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.formulario.databinding.ActivityMainBinding
import com.example.formulario.domain.usecases.AddPersonaUseCase
import com.example.formulario.domain.usecases.DeletePersonaUseCase
import com.example.formulario.domain.usecases.GetPersonasUseCase
import com.example.formulario.utils.StringProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonaUseCase(),
            GetPersonasUseCase(),
            DeletePersonaUseCase(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            //cosas
        }
    }
}