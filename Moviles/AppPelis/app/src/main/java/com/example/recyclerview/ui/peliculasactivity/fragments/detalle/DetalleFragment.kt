package com.example.recyclerview.ui.peliculasactivity.fragments.detalle

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recyclerview.databinding.FragmentDetalleBinding
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleFragment : Fragment() {

    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetalleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }

        val id = arguments?.getInt(ConstantesUI.ID)

        id?.let { DetalleEvent.LoadPelicula(it) }?.let { viewModel.handleEvent(it) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiDetalleState.collect { value ->
                    value.pelicula?.let { pelicula ->
                        binding.textoNombrePelicula.text = pelicula.original_title
                        binding.duracionTextView.text = pelicula.runtime.toString() + ConstantesUI.MINUTOS
                        binding.textoFechaEstreno.text = ConstantesUI.FECHA_DE_ESTRENO + pelicula.release_date
                        binding.revenueTextView.text = ConstantesUI.RECAUDACION + pelicula.revenue.toString() + ConstantesUI.DOLARES
                        binding.mediaVotosTextView.text =
                            ConstantesUI.ESTRELLA + pelicula.vote_average.toString()
                        binding.contadorVotosTextView.text = ConstantesUI.VOTOS + pelicula.vote_count.toString()
                        binding.posterPelicula.load("https://image.tmdb.org/t/p/w500${pelicula.poster_path}")
                    }
                }
            }
        }
    }

}