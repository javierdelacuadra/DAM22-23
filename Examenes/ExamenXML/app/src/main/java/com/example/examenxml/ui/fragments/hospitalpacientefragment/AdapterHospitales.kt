package com.example.examenxml.ui.fragments.hospitalpacientefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.examenxml.R
import com.example.examenxml.databinding.ListHospitalesBinding
import com.example.examenxml.domain.modelo.Hospital
import com.google.android.material.card.MaterialCardView
import java.util.*

class AdapterHospitales(
    private var hospitales: List<Hospital>,
    private val actions: HospitalActions,
) : RecyclerView.Adapter<HospitalesViewHolder>() {

    interface HospitalActions {
        fun cargarPacientes(hospitalID: UUID)
        fun borrarHospital(hospitalID: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HospitalesViewHolder(layoutInflater.inflate(R.layout.list_hospitales, parent, false))
    }

    override fun onBindViewHolder(holder: HospitalesViewHolder, position: Int) {
        holder.render(hospitales[position], actions)
    }

    override fun getItemCount(): Int = hospitales.size

    fun cambiarLista(lista: List<Hospital>) {
        hospitales = lista
        notifyDataSetChanged()
    }
}

class HospitalesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListHospitalesBinding.bind(view)

    fun render(
        hospital: Hospital,
        actions: AdapterHospitales.HospitalActions
    ) {

        with(binding) {
            textoUUIDHospital.text = hospital.id.toString()
            textoNombreHospital.text = hospital.nombre
            numeroCamasHospital.text = hospital.numeroCamas.toString()
            direccionHospital.text = hospital.direccion
        }

        view.findViewById<MaterialCardView>(R.id.hospitalCardView).setOnClickListener {
            val id = hospital.id
            actions.cargarPacientes(id)
        }
        view.findViewById<Button>(R.id.deleteHospitalButton).setOnClickListener {
            val id = hospital.id.toString()
            actions.borrarHospital(id)
        }
    }
}