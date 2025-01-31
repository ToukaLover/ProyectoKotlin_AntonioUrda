package com.antoniourda.proyectoantonio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antoniourda.proyectoantonio.R
import com.antoniourda.proyectoantonio.controller.Controller
import com.antoniourda.proyectoantonio.models.Ejercicio

class Adapter(
    private val ejercios: MutableList<Ejercicio>,
    private val onDelete:(Ejercicio) -> Unit,
    private val onEdit: (Ejercicio) -> Unit,
):RecyclerView.Adapter<Adapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ejercicio_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return ejercios.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val ejercicio = ejercios[position]

        holder.titleTextView.text = ejercicio.nombre
        holder.descriptionTextView.text = ejercicio.repeticionesRecomendadas.toString()

        val imageResId = holder.itemView.context.resources.getIdentifier(ejercicio.imagen, "drawable", holder.itemView.context.packageName)
        holder.imageView.setImageResource(imageResId)

        holder.imageView.setImageResource(imageResId)
        holder.delete.setOnClickListener {
            onDelete(ejercicio)
        }
        holder.edit.setOnClickListener{
            onEdit(ejercicio)
        }
    }
    fun updateList(newList: MutableList<Ejercicio>) {
        ejercios.clear()
        ejercios.addAll(newList)
        notifyDataSetChanged()
    }
    class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ejercioImage)
        val titleTextView: TextView = itemView.findViewById(R.id.ejercicioTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.ejercicioReps)
        val delete: Button = itemView.findViewById(R.id.btnEliminar)
        val edit : Button = itemView.findViewById(R.id.btnEditar)
    }

}