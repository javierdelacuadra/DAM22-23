package com.example.recyclerview.ui.usuarioactivity.fragments.pedircita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recyclerview.R
import com.example.recyclerview.databinding.FragmentPedirCitaBinding
import com.example.recyclerview.domain.modelo.Cita
import com.example.recyclerview.ui.common.ConstantesUI
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PedirCitaFragment : Fragment() {

    private var _binding: FragmentPedirCitaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PedirCitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedirCitaBinding.inflate(inflater, container, false)

        viewModel.handleEvent(PedirCitaEvent.GetEspecialidades)
        handleOnClickEvents()
        handleEnabledObjects()

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.listaEspecialidades?.let {
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, it)
                (binding.actvEspecialidad.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            state.listaDoctores?.let {
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, it)
                (binding.actvDoctores.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            state.listaFechas?.let {
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, it)
                (binding.actvFecha.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            state.listaHoras?.let {
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, it)
                (binding.actvHoras.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            state.cita?.let { cita ->
                binding.actvEspecialidad.isEnabled = false
                binding.actvDoctores.isEnabled = false
                binding.actvFecha.isEnabled = false
                binding.actvHoras.isEnabled = false
                binding.btnPedirCita.isEnabled = false
                Snackbar.make(
                    binding.root,
                    ConstantesUI.CITA_PEDIDA_CON_EXITO,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(ConstantesUI.DESHACER) {
                        viewModel.handleEvent(PedirCitaEvent.DeshacerCita(cita))
                        findNavController().navigate(R.id.action_global_pedircitafragment)
                    }
                    .show()
            }
        }
        return binding.root
    }

    private fun handleEnabledObjects() {
        binding.actvDoctores.editText?.isEnabled = false
        binding.actvFecha.editText?.isEnabled = false
        binding.actvHoras.editText?.isEnabled = false
        binding.btnPedirCita.isEnabled = false
    }

    private fun handleOnClickEvents() {

        binding.listaEspecialidad.setOnItemClickListener() { parent, _, position, _ ->
            val especialidad = parent.getItemAtPosition(position).toString()
            viewModel.handleEvent(PedirCitaEvent.ClearStateButEspecialidad)
            viewModel.handleEvent(PedirCitaEvent.GetDoctores(especialidad))
            binding.actvDoctores.isEnabled = true
            binding.actvFecha.isEnabled = false
            binding.actvHoras.isEnabled = false
            binding.btnPedirCita.isEnabled = false
            binding.actvDoctores.editText?.setText(ConstantesUI.NADA)
            binding.actvFecha.editText?.setText(ConstantesUI.NADA)
            binding.actvHoras.editText?.setText(ConstantesUI.NADA)
        }
        binding.listaDoctores.setOnItemClickListener() { parent, _, position, _ ->
            val nombreDoctor = parent.getItemAtPosition(position).toString()
            viewModel.handleEvent(PedirCitaEvent.ClearStateButEspecialidadAndDoctor)
            viewModel.handleEvent(PedirCitaEvent.GetFechas(nombreDoctor))
            binding.actvFecha.isEnabled = true
            binding.actvHoras.isEnabled = false
            binding.btnPedirCita.isEnabled = false
            binding.actvFecha.editText?.setText(ConstantesUI.NADA)
            binding.actvHoras.editText?.setText(ConstantesUI.NADA)
        }
        binding.listaFechas.setOnItemClickListener() { parent, _, position, _ ->
            val fecha = parent.getItemAtPosition(position).toString()
            val nombreDoctor = binding.actvDoctores.editText?.text.toString()
            viewModel.handleEvent(PedirCitaEvent.ClearStateButEspecialidadAndDoctorAndFecha)
            viewModel.handleEvent(PedirCitaEvent.GetHours(fecha, nombreDoctor))
            binding.actvHoras.isEnabled = true
            binding.btnPedirCita.isEnabled = false
            binding.actvHoras.editText?.setText(ConstantesUI.NADA)
        }
        binding.listaHoras.setOnItemClickListener() { _, _, _, _ ->
            binding.btnPedirCita.isEnabled = true
        }

        binding.btnPedirCita.setOnClickListener {
            val nombreDoctor = binding.actvDoctores.editText?.text.toString()
            val fecha = binding.actvFecha.editText?.text.toString()
            val hora = binding.actvHoras.editText?.text.toString()
            val cita = Cita(0, fecha, hora, ConstantesUI.EMAIL_USUARIO_PLACEHOLDER, nombreDoctor, 0)
            viewModel.handleEvent(PedirCitaEvent.PedirCita(cita))
        }
    }
}