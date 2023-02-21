package com.example.examenxml.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.examenxml.R
import com.example.examenxml.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentHospitalesContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.HospitalPacienteFragment,
                    R.id.PacientesFragment,
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavView.setupWithNavController(navController)

            bottomNavView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.hospitalesPacientes -> {
                        navController.navigate(R.id.action_global_hospitalpacientefragment)
                        true
                    }
                    R.id.pacientes -> {
                        navController.navigate(R.id.action_global_pacientesfragment)
                        true
                    }
                    else -> false
                }
            }

            topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

            navController.addOnDestinationChangedListener { _, destination, arguments ->
                topAppBar.isVisible = arguments?.getBoolean("ShowAppBar", false) == true
                topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

                when (destination.id) {
                    R.id.HospitalPacienteFragment -> {
                        topAppBar.title = "Hospitales y Pacientes"
                    }
                    R.id.PacientesFragment -> {
                        topAppBar.title = "Pacientes"
                    }
                }
            }
        }
    }
}