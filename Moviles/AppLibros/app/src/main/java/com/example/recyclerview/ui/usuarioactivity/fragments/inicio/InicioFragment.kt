package com.example.recyclerview.ui.usuarioactivity.fragments.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.recyclerview.databinding.FragmentInicioBinding
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)

        binding.drJuan.load(ConstantesUI.DR_JUAN_URL)
        binding.draIsabel.load(ConstantesUI.DRA_ISABEL_URL)
        binding.drNefario.load(ConstantesUI.DR_NEFARIO_URL)
        return binding.root
    }
}