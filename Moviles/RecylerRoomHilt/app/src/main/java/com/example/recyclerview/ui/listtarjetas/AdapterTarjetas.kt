package com.example.recyclerview.ui.listtarjetas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.TarjetaListViewBinding
import com.example.recyclerview.domain.modelo.Tarjeta

class AdapterTarjetas(
    private var listaTarjetas: List<Tarjeta>
) : RecyclerView.Adapter<TarjetasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarjetasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TarjetasViewHolder(layoutInflater.inflate(R.layout.tarjeta_list_view, parent, false))
    }

    override fun onBindViewHolder(holder: TarjetasViewHolder, position: Int) {
        holder.render(listaTarjetas[position])
    }

    override fun getItemCount() = listaTarjetas.size

    fun cambiarLista(listaTarjetas: List<Tarjeta>) {
        this.listaTarjetas = listaTarjetas
        notifyDataSetChanged()
    }
}

class TarjetasViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = TarjetaListViewBinding.bind(view)

    fun render(
        tarjeta: Tarjeta,
    ) {

        with(binding) {
            numeroTextField.text = tarjeta.numeroTarjeta
            fechaCaducidadTextField.text = tarjeta.fechaCaducidad
            cvvTextField.text = tarjeta.cvv.toString()
            emailTextField.text = tarjeta.email
        }
    }
}