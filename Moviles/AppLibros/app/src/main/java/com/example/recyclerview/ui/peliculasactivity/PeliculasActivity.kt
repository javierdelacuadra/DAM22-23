package com.example.recyclerview.ui.peliculasactivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityPeliculasBinding
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.usuarioactivity.UsuarioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeliculasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeliculasBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeliculasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.PelisInicioFragment,
                    R.id.BuscarFragment,
                    R.id.TrendingFragment,
                    R.id.TopRatedFragment,
                    R.id.UpcomingFragment,
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavView.setupWithNavController(navController)

            bottomNavView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.PelisInicioFragment -> {
                        navController.navigate(R.id.PelisInicioFragment)
                        true
                    }
                    R.id.BuscarFragment -> {
                        navController.navigate(R.id.BuscarFragment)
                        true
                    }
                    R.id.TrendingFragment -> {
                        navController.navigate(R.id.TrendingFragment)
                        true
                    }
                    R.id.TopRatedFragment -> {
                        navController.navigate(R.id.TopRatedFragment)
                        true
                    }
                    R.id.UpcomingFragment -> {
                        navController.navigate(R.id.UpcomingFragment)
                        true
                    }
                    else -> false
                }
            }

            topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

            navController.addOnDestinationChangedListener { _, destination, arguments ->
                topAppBar.isVisible = arguments?.getBoolean(ConstantesUI.SHOWAPPBAR, false) == true
                topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

                when (destination.id) {
                    R.id.PelisInicioFragment -> {
                        topAppBar.title = "Inicio"
                    }
                    R.id.BuscarFragment -> {
                        topAppBar.title = "Buscar"
                    }
                    R.id.TrendingFragment -> {
                        topAppBar.title = "Trending"
                    }
                    R.id.TopRatedFragment -> {
                        topAppBar.title = "Top Rated"
                    }
                    R.id.UpcomingFragment -> {
                        topAppBar.title = "Upcoming"
                    }
                }
            }
        }
    }
}