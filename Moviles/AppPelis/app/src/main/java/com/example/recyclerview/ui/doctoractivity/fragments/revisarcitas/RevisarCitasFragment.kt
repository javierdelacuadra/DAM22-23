package com.example.recyclerview.ui.doctoractivity.fragments.revisarcitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.FragmentRevisarCitasBinding
import com.example.recyclerview.domain.modelo.Cita
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RevisarCitasFragment : Fragment() {
    private var _binding: FragmentRevisarCitasBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaCitas: RecyclerView

    private val viewModel: RevisarCitasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRevisarCitasBinding.inflate(inflater, container, false)

        viewModel.handleEvent(RevisarCitasEvent.GetCitas)

        val adapter = AdapterCitasDoctor(ArrayList(),
            object : AdapterCitasDoctor.CitaActions {
                override fun marcarComoRealizada(cita: Cita) {
                    marcarCita(cita)
                }
            })

        listaCitas = binding.listaCitasDoctor
        listaCitas.adapter = adapter
        listaCitas.layoutManager = GridLayoutManager(requireContext(), 1)

        binding.noCitasPorRevisar.visibility = View.GONE

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.citas.let { listaCitas ->
                if (listaCitas.isEmpty()) {
                    binding.noCitasPorRevisar.visibility = View.VISIBLE
                } else {
                    binding.noCitasPorRevisar.visibility = View.GONE
                }
                adapter.cambiarLista(listaCitas)
            }
            state.mensaje?.let {
                Timber.i(it)
                if (it == ConstantesUI.CITA_CONFIRMADA) {
                    viewModel.handleEvent(RevisarCitasEvent.GetCitas)
                }
            }
        }
        return binding.root
    }

    private fun marcarCita(cita: Cita) {
        viewModel.handleEvent(RevisarCitasEvent.MarcarComoRealizada(cita))
    }
}