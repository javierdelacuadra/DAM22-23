package com.example.examenxml.ui.fragments.hospitalpacientefragment

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
import com.example.examenxml.databinding.FragmentHospitalPacienteBinding
import com.example.examenxml.network.utils.ConnectionUtils
import com.example.examenxml.ui.fragments.detallefragment.DetalleFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HospitalPacienteFragment : Fragment() {

    private var _binding: FragmentHospitalPacienteBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaHospitales: RecyclerView
    private lateinit var listaPacientes: RecyclerView

    private val viewModel: HospitalPacienteViewModel by viewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalPacienteBinding.inflate(inflater, container, false)

        viewModel.handleEvent(HospitalPacienteEvent.Eventos.LoadHospitales)

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

        val adapterHospitales =
            AdapterHospitales(ArrayList(), object : AdapterHospitales.HospitalActions {
                override fun cargarPacientes(hospitalID: UUID) {
                    viewModel.handleEvent(HospitalPacienteEvent.Eventos.LoadPacientes(hospitalID.toString()))
                }

                override fun borrarHospital(hospitalID: String) {
                    viewModel.handleEvent(HospitalPacienteEvent.Eventos.BorrarHospital(hospitalID))
                }
            })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiHospitalPacienteState.collect { value ->
                    binding.loadingHospitales.visibility = if (value.cargando) View.VISIBLE else View.GONE
                    value.hospitales?.let { adapterHospitales.cambiarLista(it) }
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

        viewModel.uiHospitalPacienteState.value.pacientes?.let { adapterPacientes.cambiarLista(it) }

        listaHospitales = binding.listaHospitales
        listaHospitales.adapter = adapterHospitales
        listaHospitales.layoutManager = GridLayoutManager(requireContext(), 1)

        listaPacientes = binding.listaPacientes
        listaPacientes.adapter = adapterPacientes
        listaPacientes.layoutManager = GridLayoutManager(requireContext(), 1)

        return binding.root
    }

    //recycler hospitales
    //botón borrar hospital al completo
    //on click de uno, recycler pacientes de ese hospital
    //on click de un paciente lleva al detalle
}