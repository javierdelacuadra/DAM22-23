package com.example.recyclerview.ui.peliculasactivity.fragments.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerview.databinding.FragmentPelisInicioBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PelisInicioFragment : Fragment() {
    private var _binding: FragmentPelisInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPelisInicioBinding.inflate(inflater, container, false)
        return binding.root
    }
}