package com.example.recyclerview.ui.peliculasactivity.fragments.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.FragmentTrendingBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaPeliculas: RecyclerView

    private val viewModel: TrendingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)

        viewModel.handleEvent(TrendingEvent.Eventos.LoadPeliculas)

        val adapter = AdapterPeliculas(ArrayList())

        listaPeliculas = binding.listaPelisTrending
        listaPeliculas.adapter = adapter
        listaPeliculas.layoutManager = GridLayoutManager(requireContext(), 1)


        return binding.root
    }

}