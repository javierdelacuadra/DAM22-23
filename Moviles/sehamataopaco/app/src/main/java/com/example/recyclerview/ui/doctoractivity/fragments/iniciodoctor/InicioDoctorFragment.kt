package com.example.recyclerview.ui.doctoractivity.fragments.iniciodoctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerview.databinding.FragmentInicioDoctorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InicioDoctorFragment : Fragment() {

    private var _binding: FragmentInicioDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioDoctorBinding.inflate(inflater, container, false)

        return binding.root
    }
}