package com.example.recyclerview.ui.usuarioactivity.fragments.vercitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.FragmentVerCitasBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class VerCitasFragment : Fragment() {

    private var _binding: FragmentVerCitasBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaCitas: RecyclerView

    private val viewModel: VerCitasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerCitasBinding.inflate(inflater, container, false)

        viewModel.handleEvent(VerCitasEvent.GetCitas)

        val adapter = AdapterCitas(ArrayList())

        listaCitas = binding.listaCitasUsuario
        listaCitas.adapter = adapter
        listaCitas.layoutManager = GridLayoutManager(requireContext(), 1)

        binding.noCitasPorVer.visibility = View.GONE

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.citas.let { listaCitas ->
                if (listaCitas.isEmpty()) {
                    binding.noCitasPorVer.visibility = View.VISIBLE
                } else {
                    adapter.cambiarLista(listaCitas)
                    binding.noCitasPorVer.visibility = View.GONE
                }
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}