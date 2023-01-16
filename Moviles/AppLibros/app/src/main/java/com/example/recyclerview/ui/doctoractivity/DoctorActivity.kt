package com.example.recyclerview.ui.doctoractivity

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
import com.example.recyclerview.databinding.ActivityDoctorBinding
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.loginactivity.LoginActivity
import com.example.recyclerview.ui.usuarioactivity.UsuarioEvent
import com.example.recyclerview.ui.usuarioactivity.UsuarioViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.doctoriniciofragment,
                    R.id.revisarcitasFragment,
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
                    R.id.doctoriniciofragment -> {
                        navController.navigate(R.id.action_global_doctoriniciofragment)
                        drawerLayout.close()
                    }
                    R.id.revisarcitasFragment -> {
                        navController.navigate(R.id.action_global_revisarcitasfragment)
                        drawerLayout.close()
                    }
                    R.id.repositorioAccess -> {
                        val repoURL = Uri.parse(ConstantesUI.LINK_GITHUB)
                        val intent = Intent(Intent.ACTION_VIEW, repoURL)
                        startActivity(intent)
                        drawerLayout.close()
                    }
                    R.id.cerrarSesion -> {
                        drawerLayout.close()
                        viewModel.handleEvent(UsuarioEvent.CerrarSesion)
                        val intent = Intent(this@DoctorActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.acercaDe -> {
                        drawerLayout.close()
                        val dialog = MaterialAlertDialogBuilder(this@DoctorActivity)
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
                    R.id.doctoriniciofragment -> {
                        topAppBar.title = ConstantesUI.INICIO
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_baseline_menu_24)
                    }
                    R.id.revisarcitasFragment -> {
                        topAppBar.title = ConstantesUI.REVISAR_CITAS
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