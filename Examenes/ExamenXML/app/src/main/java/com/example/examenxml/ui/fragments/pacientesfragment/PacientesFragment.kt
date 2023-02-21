package com.example.examenxml.ui.fragments.pacientesfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examenxml.R
import com.example.examenxml.databinding.FragmentPacientesBinding
import com.example.examenxml.network.utils.ConnectionUtils
import com.example.examenxml.ui.fragments.detallefragment.DetalleFragment
import com.example.examenxml.ui.fragments.hospitalpacientefragment.AdapterPacientes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class PacientesFragment : Fragment() {
    private var _binding: FragmentPacientesBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaPacientes: RecyclerView

    private val viewModel: PacienteViewModel by viewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPacientesBinding.inflate(inflater, container, false)
        viewModel.handleEvent(PacienteEvent.Eventos.LoadPacientes)

        val adapterPacientes =
            AdapterPacientes(ArrayList(), object : AdapterPacientes.PacienteActions {
                override fun verDetalle(hospitalID: UUID) {
                    ConnectionUtils.hasInternetConnection(requireContext()).let { hasInternet ->
                        if (hasInternet) {
                            val bundle = Bundle()
                            bundle.putString("uuid", hospitalID.toString())
                            val fragment = DetalleFragment()
                            fragment.arguments = bundle
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentHospitalesContainerView, fragment)
                                .addToBackStack(null)
                                .commit()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "No tienes conexión a Internet",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiPacienteState.collect { value ->
                    binding.loadingPacientes.visibility = if (value.cargando) View.VISIBLE else View.GONE
                    value.pacientes?.let { adapterPacientes.cambiarLista(it) }
                    value.error.let {
                        value.error?.let { it1 ->
                            Snackbar.make(
                                binding.root,
                                it1,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        listaPacientes = binding.listaPacientes
        listaPacientes.adapter = adapterPacientes
        listaPacientes.layoutManager = GridLayoutManager(requireContext(), 1)
        return binding.root

    }


    //recycler todos los pacientes de todos los hospitales
    //pinchar en uno lleva al detalle
    //filtrar por nombre de usuario (no)
}