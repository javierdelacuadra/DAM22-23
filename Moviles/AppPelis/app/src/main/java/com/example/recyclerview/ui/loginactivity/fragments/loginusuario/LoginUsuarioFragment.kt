package com.example.recyclerview.ui.loginactivity.fragments.loginusuario

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclerview.databinding.FragmentLoginUsuarioBinding
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.usuarioactivity.UsuarioActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginUsuarioFragment : Fragment() {

    private var _binding: FragmentLoginUsuarioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginUsuarioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginUsuarioBinding.inflate(layoutInflater)

        with(binding) {
            btnLogin.setOnClickListener {
                checkLogin()
            }
            botonInfo.setOnClickListener {
                mostrarInfo()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.loginSuccess?.let {
                if (it) {
                    Timber.i(ConstantesUI.LOGIN_CORRECTO_COMO_USUARIO)
                    Snackbar.make(
                        requireView(),
                        ConstantesUI.INICIO_DE_SESION_CORRECTO,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    loadInicioUsuario()
                } else {
                    Timber.i(ConstantesUI.LOGIN_INCORRECTO_COMO_USUARIO)
                    Snackbar.make(
                        requireView(),
                        ConstantesUI.EL_NOMBRE_O_LA_CONTRASENA_SON_INCORRECTOS,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return binding.root
    }

    private fun loadInicioUsuario() {
        val intent = Intent(requireContext(), UsuarioActivity::class.java)
        startActivity(intent)
    }

    private fun checkLogin() {
        val nombre = binding.nombreUsuario.editText?.text.toString()
        val password = binding.passwordUsuario.editText?.text.toString()
        viewModel.handleEvent(LoginUsuarioEvent.Login(nombre, password))
    }

    private fun mostrarInfo() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(ConstantesUI.INFORMACION)
            .setMessage(ConstantesUI.INFORMACION_LOGIN)
            .setPositiveButton(ConstantesUI.ACEPTAR) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}