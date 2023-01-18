package com.example.recyclerview.ui.peliculasactivity.fragments.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.FragmentPelisInicioBinding
import com.example.recyclerview.databinding.FragmentTopRatedBinding
import com.example.recyclerview.ui.peliculasactivity.fragments.toprated.TopRatedViewModel
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