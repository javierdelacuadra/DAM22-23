package com.example.recyclerview.ui.peliculasactivity.fragments.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ListPeliculasBinding
import com.example.recyclerview.domain.modelo.Pelicula
import com.google.android.material.button.MaterialButton

class AdapterPeliculas(
    private var peliculas: List<Pelicula>,
    //private val actions: PeliculaActions,
) : RecyclerView.Adapter<PeliculasViewHolder>() {

    interface PeliculaActions {
//        fun marcarComoRealizada(cita: Cita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeliculasViewHolder(layoutInflater.inflate(R.layout.list_peliculas, parent, false))
    }

    override fun onBindViewHolder(holder: PeliculasViewHolder, position: Int) {
//        holder.render(peliculas[position], actions)
        holder.render(peliculas[position])
    }

    override fun getItemCount(): Int = peliculas.size

    fun cambiarLista(lista: List<Pelicula>) {
        peliculas = lista
        notifyDataSetChanged()
    }
}

class PeliculasViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListPeliculasBinding.bind(view)

    fun render(
        pelicula: Pelicula,
        //actions: AdapterPeliculas.PeliculaActions
    ) {

        with(binding) {
            textoNombrePelicula.text = pelicula.title
            fechaEstreno.text = pelicula.release_date
            textorating.text = "\u2605" + pelicula.vote_average.toString()
        }

        view.findViewById<MaterialButton>(R.id.btnMarcarCita).setOnClickListener {
//            actions.marcarComoRealizada(pelicula)
        }
    }
}