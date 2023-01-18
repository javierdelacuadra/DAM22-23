package com.example.recyclerview.ui.peliculasactivity.fragments.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.FragmentTopRatedBinding
import com.example.recyclerview.ui.peliculasactivity.fragments.trending.AdapterPeliculas
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedFragment : Fragment() {
    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaPeliculas: RecyclerView

    private val viewModel: TopRatedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)

        viewModel.handleEvent(TopRatedEvent.Eventos.LoadPeliculas)

        val adapter = AdapterPeliculas(ArrayList())

        listaPeliculas = binding.listaPelisTopRated
        listaPeliculas.adapter = adapter
        listaPeliculas.layoutManager = GridLayoutManager(requireContext(), 1)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiTopRatedState.collect { value ->
//                    when (value) {
//                        is UiState.Failure -> {
//                            Toast.makeText(this@MainActivity, value.mensaje, Toast.LENGTH_SHORT)
//                                .show()
//                            binding.loading.visibility = View.GONE
//                        }
                    binding.loading.visibility = if (value.cargando) View.VISIBLE else View.GONE
                    value.movies?.let { adapter.cambiarLista(it) }
                    value.error.let {
//                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
//                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiError.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}