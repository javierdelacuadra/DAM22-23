package com.example.recyclerview.ui.mainactivity;

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.recyclerview.R
import com.example.recyclerview.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(topAppBar)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottomnavigationbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.page_1 -> {
                //load InicioFragment
                item.onNavDestinationSelected(navController)
                true
            }
            R.id.page_2 -> {
                true
            }
            R.id.page_3 -> {
                true
            }
            R.id.page_4 -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadInicioFragment() {

    }
}
