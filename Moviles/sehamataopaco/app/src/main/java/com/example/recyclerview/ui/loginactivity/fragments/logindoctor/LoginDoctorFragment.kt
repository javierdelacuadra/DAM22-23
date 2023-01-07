package com.example.recyclerview.ui.loginactivity.fragments.logindoctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclerview.databinding.FragmentLoginDoctorBinding
import com.example.recyclerview.ui.doctoractivity.DoctorActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginDoctorFragment : Fragment() {

    private var _binding: FragmentLoginDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginDoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginDoctorBinding.inflate(layoutInflater)

        with(binding) {
            btnLogin.setOnClickListener {
                checkLogin()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.loginSuccess?.let {
                if (it) {
                    Timber.i("Login correcto como doctor")
                    Snackbar.make(requireView(), "Inicio de sesión correcto", Snackbar.LENGTH_SHORT)
                        .show()
                    loadInicioDoctor()
                } else {
                    Timber.i("Login incorrecto como doctor")
                    Snackbar.make(
                        requireView(),
                        "El nombre o el email son incorrectos",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return binding.root
    }

    private fun loadInicioDoctor() {
        val intent = Intent(requireContext(), DoctorActivity::class.java)
        startActivity(intent)
    }

    private fun checkLogin() {
        val nombre = binding.nombreDoctor.editText?.text.toString()
        val email = binding.emailDoctor.editText?.text.toString()
        viewModel.handleEvent(LoginDoctorEvent.Login(nombre, email))
    }
}