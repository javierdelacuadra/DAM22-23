package com.example.recyclerview.ui.loginactivity.fragments.logindoctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclerview.databinding.FragmentLoginDoctorBinding
import com.example.recyclerview.ui.common.ConstantesUI
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
        _binding = FragmentLoginDoctorBinding.inflate(layoutInflater)

        with(binding) {
            btnLogin.setOnClickListener {
                checkLogin()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.loginSuccess?.let {
                if (it) {
                    Timber.i(ConstantesUI.LOGIN_CORRECTO_COMO_DOCTOR)
                    Snackbar.make(
                        requireView(),
                        ConstantesUI.INICIO_DE_SESION_CORRECTO,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    loadInicioDoctor()
                } else {
                    Timber.i(ConstantesUI.LOGIN_INCORRECTO_COMO_DOCTOR)
                    Snackbar.make(
                        requireView(),
                        ConstantesUI.EL_NOMBRE_O_EL_EMAIL_SON_INCORRECTOS,
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