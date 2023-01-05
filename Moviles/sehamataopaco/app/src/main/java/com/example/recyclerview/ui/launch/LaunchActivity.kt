package com.example.recyclerview.ui.launch

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityLaunchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
//
            setSupportActionBar(topAppBar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.InicioFragment, R.id.PedirCitaFragment, R.id.verCitasFragment, R.id.configFragment, R.id.acercaDeFragment
            ), drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            topAppBar.setNavigationOnClickListener {
                Log.i("TAG", navController.currentDestination?.id.toString())
                drawerLayout.open()
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.PedirCitaFragment -> {
                        navController.navigate(R.id.PedirCitaFragment)
                        true
                    }
                    R.id.verCitasFragment -> {
                        navController.navigate(R.id.verCitasFragment)
                        true
                    }
                    R.id.configFragment -> {
                        navController.navigate(R.id.configFragment)
                        true
                    }
                    R.id.acercaDeFragment -> {
                        navController.navigate(R.id.acercaDeFragment)
                        true
                    }
                    else -> false
                }
            }


            topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

            navController.addOnDestinationChangedListener { _, destination, arguments ->
                topAppBar.isVisible = arguments?.getBoolean("ShowAppBar", false) == true
                topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

                when (destination.id){
                    R.id.InicioFragment -> {
                        topAppBar.title = "Inicio"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)
                    }
                    R.id.PedirCitaFragment -> {
                        topAppBar.title = "Pedir Cita"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                    }
                    R.id.verCitasFragment -> {
                        topAppBar.title = "Ver Citas"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                    }
                    R.id.configFragment -> {
                        topAppBar.title = "Configuración"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                    }
                    R.id.acercaDeFragment -> {
                        topAppBar.title = "Acerca de"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                    }
                }
            }

//            supportActionBar?.setHomeButtonEnabled(true)
//            topAppBar.visibility = View.GONE


        }
    }
}