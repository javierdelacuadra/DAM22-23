package com.example.recyclerview.ui.listactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ListViewBinding
import com.example.recyclerview.domain.modelo.Persona

class AdapterPersonas(
    private var personas: List<Persona>,
    private val deletePersona: (Persona) -> Unit,
    private val updatePersona: (String, String) -> Unit,
) : RecyclerView.Adapter<PersonasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonasViewHolder(layoutInflater.inflate(R.layout.list_view, parent, false))
    }

    override fun onBindViewHolder(holder: PersonasViewHolder, position: Int) {
        holder.render(personas[position], deletePersona, updatePersona)
    }

    override fun getItemCount(): Int = personas.size

    fun cambiarLista(lista: List<Persona>) {
        personas = lista
        notifyDataSetChanged()
    }
}

class PersonasViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListViewBinding.bind(view)

    fun render(
        persona: Persona,
        deletePersona: (Persona) -> Unit,
        updatePersona: (String, String) -> Unit,
    ) {

        with(binding) {
            nombreTextField.text = persona.nombre
            emailTextField.text = persona.email
        }

        view.findViewById<ImageButton>(R.id.buttonDelete).setOnClickListener {
            deletePersona(Persona(persona.nombre, persona.password, persona.email))
        }

        view.findViewById<ImageButton>(R.id.buttonUpdate).setOnClickListener {
            updatePersona(binding.nombreTextField.text.toString(), binding.emailTextField.text.toString())
        }
    }
}