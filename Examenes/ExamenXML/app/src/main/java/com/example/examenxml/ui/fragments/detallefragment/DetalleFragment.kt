package com.example.examenxml.ui.fragments.detallefragment

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examenxml.databinding.FragmentDetalleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    @SuppressLint("UnsafeRepeatOnLifecycleDetector", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }

        val id = arguments?.getString("uuid")

        id?.let { DetalleEvent.LoadPaciente(it) }?.let { viewModel.handleEvent(it) }

        val adapter = AdapterEnfermedades(ArrayList())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiDetalleState.collect { value ->
                    value.paciente?.let { pelicula ->
                        binding.idPaciente.text = pelicula.id.toString()
                        binding.nombrePaciente.editText?.setText(pelicula.nombre)
                        binding.dniPaciente.text = pelicula.dni
                        if (pelicula.enfermedades.isEmpty()) {
                            binding.listaEnfermedades.visibility = View.GONE
                            binding.mensajeLista.visibility = View.VISIBLE
                            binding.mensajeLista.text = "No hay enfermedades"
                        } else {
                            binding.listaEnfermedades.visibility = View.VISIBLE
                            adapter.cambiarLista(pelicula.enfermedades)
                        }
                    }
                }
            }
        }

        listaEnfermedades = binding.listaEnfermedades
        listaEnfermedades.adapter = adapter
        listaEnfermedades.layoutManager = GridLayoutManager(requireContext(), 1)
    }

}
//datos del paciente
//recyclerview enfermedades
//actualizar nombre paciente
//añadir enfermedad