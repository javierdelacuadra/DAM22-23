package com.example.recyclerview.ui.peliculasactivity.fragments.trending

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
import com.example.recyclerview.R
import com.example.recyclerview.databinding.FragmentTrendingBinding
import com.example.recyclerview.ui.peliculasactivity.fragments.detalle.DetalleFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        val adapter = AdapterPeliculas(ArrayList(), object : AdapterPeliculas.PeliculaActions {
            override fun verDetalle(peliculaID: Int) {
                val bundle = Bundle()
                bundle.putInt("id", peliculaID)
                val fragment = DetalleFragment()
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentPelisContainerView, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        listaPeliculas = binding.listaPelisTrending
        listaPeliculas.adapter = adapter
        listaPeliculas.layoutManager = GridLayoutManager(requireContext(), 1)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiTrendingState.collect { value ->
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