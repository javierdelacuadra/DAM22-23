package com.example.examenxml.ui.fragments.detallefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.data.modelo.toEnfermedad
import com.example.examenxml.databinding.FragmentDetalleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetalleFragment : Fragment() {

    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaEnfermedades: RecyclerView

    private val viewModel: DetalleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }

        val id = arguments?.getString("uuid")

        id?.let { DetalleEvent.LoadPaciente(it) }?.let { viewModel.handleEvent(it) }

        val adapter = AdapterEnfermedades(ArrayList())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiDetalleState.collect { value ->
                    value.paciente?.let { paciente ->
                        binding.idPaciente.text = paciente.paciente.id.toString()
                        binding.nombrePaciente.editText?.text?.clear()
                        binding.nombrePaciente.editText?.setText(paciente.paciente.nombre)
                        binding.dniPaciente.text = paciente.paciente.dni
                        val enfermedades = paciente.enfermedades.map { it.toEnfermedad() }
                        adapter.cambiarLista(enfermedades)
                        Toast.makeText(requireContext(), "Recarga la pantalla para ver el cambio de nombre", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        listaEnfermedades = binding.listaEnfermedades
        listaEnfermedades.adapter = adapter
        listaEnfermedades.layoutManager = GridLayoutManager(requireContext(), 1)

        binding.botonAddEnfermedad.setOnClickListener {
            val id = binding.idPaciente.text.toString()
            val enfermedad = binding.nombreEnfermedad.editText?.text.toString()
            val enfermedadEntity = EnfermedadEntity(enfermedad, UUID.fromString(id))
            viewModel.handleEvent(DetalleEvent.AddEnfermedad(enfermedadEntity))
        }

        binding.botonEditar.setOnClickListener {
            val id = binding.idPaciente.text.toString()
            val nombre = binding.nombrePaciente.editText?.text.toString()
            viewModel.handleEvent(DetalleEvent.EditarPaciente(id, nombre))
        }
    }
}