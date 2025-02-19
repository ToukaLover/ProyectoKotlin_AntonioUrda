package com.antoniourda.proyectoantonio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antoniourda.proyectoantonio.R
import com.antoniourda.proyectoantonio.models.Ejercicio

class Adapter(
    private val ejercicios: MutableList<Ejercicio>,
    private val onDelete: (Ejercicio) -> Unit,
    private val onEdit: (Ejercicio) -> Unit,
) : RecyclerView.Adapter<Adapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ejercicio_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return ejercicios.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val ejercicio = ejercicios[position]

        holder.titleTextView.text = ejercicio.nombre
        holder.descriptionTextView.text = ejercicio.repeticionesRecomendadas.toString()

        // Cargar la imagen desde los recursos drawable
        val imageResId = holder.itemView.context.resources.getIdentifier(ejercicio.imagen, "drawable", holder.itemView.context.packageName)
        if (imageResId != 0) {
            holder.imageView.setImageResource(imageResId)
        } else {
            // Si no se encuentra la imagen, puedes poner una imagen por defecto o dejarla vacía
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground) // Ejemplo de imagen por defecto
        }

        holder.delete.setOnClickListener {
            onDelete(ejercicio)
        }
        holder.edit.setOnClickListener {
            onEdit(ejercicio)
        }
    }

    fun updateList(newList: MutableList<Ejercicio>) {
        ejercicios.clear()
        ejercicios.addAll(newList)
        notifyDataSetChanged()
    }

    fun getEjercicios(): MutableList<Ejercicio> {
        return ejercicios
    }

    fun getItemPosition(ejercicio: Ejercicio): Int {
        return ejercicios.indexOf(ejercicio)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ejercioImage)
        val titleTextView: TextView = itemView.findViewById(R.id.ejercicioTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.ejercicioReps)
        val delete: Button = itemView.findViewById(R.id.btnEliminar)
        val edit: Button = itemView.findViewById(R.id.btnEditar)
    }
}