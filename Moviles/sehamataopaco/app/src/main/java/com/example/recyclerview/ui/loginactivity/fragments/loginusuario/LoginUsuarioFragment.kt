package com.example.recyclerview.ui.loginactivity.fragments.loginusuario

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclerview.databinding.FragmentLoginUsuarioBinding
import com.example.recyclerview.ui.usuarioactivity.UsuarioActivity
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
        // Inflate the layout for this fragment
        _binding = FragmentLoginUsuarioBinding.inflate(layoutInflater)

        with(binding) {
            btnLogin.setOnClickListener {
                checkLogin()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.loginSuccess?.let {
                if (it) {
                    Timber.i("Login correcto como usuario")
                    Snackbar.make(requireView(), "Inicio de sesión correcto", Snackbar.LENGTH_SHORT)
                        .show()
                    loadInicioUsuario()
                } else {
                    Timber.i("Login incorrecto como usuario")
                    Snackbar.make(
                        requireView(),
                        "El nombre o la contraseña son incorrectos",
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
}