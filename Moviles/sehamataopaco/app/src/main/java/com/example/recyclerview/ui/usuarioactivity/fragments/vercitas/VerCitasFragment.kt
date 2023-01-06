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
import com.example.recyclerview.domain.modelo.Cita
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
        // Inflate the layout for this fragment
        _binding = FragmentVerCitasBinding.inflate(inflater, container, false)

        viewModel.handleEvent(VerCitasEvent.GetCitas)

        val adapter = AdapterCitas(ArrayList(),
            object : AdapterCitas.CitaActions {
                override fun cancelarCita(cita: Cita) {
                    eliminarCita(cita)
                }
            })

        listaCitas = binding.listaCitasUsuario
        listaCitas.adapter = adapter
        listaCitas.layoutManager = GridLayoutManager(requireContext(), 1)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.citas.let { listaCitas ->
                adapter.cambiarLista(listaCitas)
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun eliminarCita(cita: Cita) {
        viewModel.handleEvent(VerCitasEvent.CancelarCita(cita))
    }
}