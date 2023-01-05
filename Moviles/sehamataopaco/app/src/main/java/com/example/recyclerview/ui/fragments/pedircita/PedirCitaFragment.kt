package com.example.recyclerview.ui.fragments.pedircita

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclerview.R
import com.example.recyclerview.databinding.FragmentPedirCitaBinding
import com.example.recyclerview.domain.modelo.Cita
import timber.log.Timber
import java.time.LocalDate

class PedirCitaFragment : Fragment() {

    private var _binding: FragmentPedirCitaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PedirCitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPedirCitaBinding.inflate(inflater, container, false)

        viewModel.handleEvent(PedirCitaEvent.GetEspecialidades)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.listaEspecialidades.let {
                val items = viewModel.uiState.value?.listaEspecialidades
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items!!)
                binding.actvEspecialidad.setAdapter(adapter)
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.actvEspecialidad.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            binding.actvDoctores.isEnabled = true
            viewModel.handleEvent(PedirCitaEvent.GetDoctores(selectedItem))
            val doctorItems = viewModel.uiState.value?.listaDoctores
            val doctorAdapter = ArrayAdapter(requireContext(), R.layout.list_item, doctorItems!!)
            binding.actvDoctores.setAdapter(doctorAdapter)
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val date = LocalDate.of(year, month + 1, dayOfMonth)
                binding.actvFecha.setText(date.toString())
            },
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        viewModel.handleEvent(PedirCitaEvent.GetHours(binding.actvDoctores.text.toString()))
        val hours = viewModel.uiState.value?.listaHoras
        val hoursAdapter = ArrayAdapter(requireContext(), R.layout.list_item, hours!!)
        binding.actvHoras.setAdapter(hoursAdapter)

        binding.btnPedirCita.setOnClickListener {
            binding.actvEspecialidad.text.toString()
            val doctor = binding.actvDoctores.text.toString()
            val fecha = binding.actvFecha.text.toString()
            val hora = binding.actvHoras.text.toString()
            val cita = Cita(0, fecha, hora, "userPlaceHolder", doctor)
        }

        return binding.root
    }
}