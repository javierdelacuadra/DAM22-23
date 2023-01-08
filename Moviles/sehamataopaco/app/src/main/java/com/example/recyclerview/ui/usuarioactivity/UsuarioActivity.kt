package com.example.recyclerview.ui.usuarioactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityUsuarioBinding
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.loginactivity.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuarioBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()


            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.InicioFragment,
                    R.id.PedirCitaFragment,
                    R.id.verCitasFragment,
                    R.id.repositorioAccess,
                    R.id.cerrarSesion,
                    R.id.acercaDe
                ), drawerLayout
            )


            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            topAppBar.setNavigationOnClickListener {
                Log.i(ConstantesUI.TAG, navController.currentDestination?.id.toString())
                drawerLayout.open()
            }

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.inicioFragment -> {
                        navController.navigate(R.id.action_global_iniciofragment)
                        drawerLayout.close()
                    }
                    R.id.pedirCitaFragment -> {
                        navController.navigate(R.id.action_global_pedircitafragment)
                        drawerLayout.close()
                    }
                    R.id.verCitasFragment -> {
                        navController.navigate(R.id.action_global_vercitasfragment)
                        drawerLayout.close()
                    }
                    R.id.repositorioAccess -> {
                        val myProfileURI = Uri.parse(ConstantesUI.LINK_GITHUB)
                        val intent = Intent(Intent.ACTION_VIEW, myProfileURI)
                        startActivity(intent)
                        drawerLayout.close()
                    }
                    R.id.cerrarSesion -> {
                        drawerLayout.close()
                        viewModel.handleEvent(UsuarioEvent.CerrarSesion)
                        val intent = Intent(this@UsuarioActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.acercaDe -> {
                        drawerLayout.close()
                        val dialog = MaterialAlertDialogBuilder(this@UsuarioActivity)
                            .setTitle(ConstantesUI.ACERCA_DE)
                            .setMessage(ConstantesUI.MENSAJE_ACERCA_DE)
                            .setPositiveButton(ConstantesUI.ACEPTAR) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                        dialog.show()
                    }
                }
                true
            }

            topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

            navController.addOnDestinationChangedListener { _, destination, arguments ->
                topAppBar.isVisible = arguments?.getBoolean(ConstantesUI.SHOWAPPBAR, false) == true
                topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)

                when (destination.id) {
                    R.id.InicioFragment -> {
                        topAppBar.title = ConstantesUI.INICIO
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)
                    }
                    R.id.PedirCitaFragment -> {
                        topAppBar.title = ConstantesUI.PEDIR_CITA
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                    }
                    R.id.verCitasFragment -> {
                        topAppBar.title = ConstantesUI.VER_CITAS
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}