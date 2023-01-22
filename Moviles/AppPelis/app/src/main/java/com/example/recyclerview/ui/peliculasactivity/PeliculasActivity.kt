package com.example.recyclerview.ui.peliculasactivity

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
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityPeliculasBinding
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeliculasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeliculasBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint(ConstantesUI.USECOMPATLOADINGFORDRAWABLES)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeliculasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentPelisContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.trendingfragment,
                    R.id.topratedfragment,
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavView.setupWithNavController(navController)

            bottomNavView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.topRated -> {
                        navController.navigate(R.id.action_global_topratedfragment)
                        true
                    }
                    R.id.trending -> {
                        navController.navigate(R.id.action_global_trendingfragment)
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
                    R.id.trendingfragment -> {
                        topAppBar.title = ConstantesUI.TRENDING
                    }
                    R.id.topratedfragment -> {
                        topAppBar.title = ConstantesUI.TOP_RATED
                    }
                }
            }
        }
    }
}