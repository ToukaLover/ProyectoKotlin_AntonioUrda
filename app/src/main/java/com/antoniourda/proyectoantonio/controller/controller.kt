package com.antoniourda.proyectoantonio.controller

import android.content.Context
import com.antoniourda.proyectoantonio.models.Ejercicio

class Controller(private val context: Context) {
    private val ejerciciosList = mutableListOf<Ejercicio>()
    private var nextId = 1 // Para generar IDs autoincrementales

    interface OnDataChangeListener {
        fun onDataChanged()
    }

    private var listener: OnDataChangeListener? = null

    fun setOnDataChangeListener(listener: OnDataChangeListener) {
        this.listener = listener
    }

    fun cargarEjercicios() {
        // Aquí puedes añadir ejercicios de ejemplo si quieres
        // Por ejemplo:
        // agregarEjercicio(Ejercicio(nombre = "Flexiones", repeticionesRecomendadas = 10, imagen = "flexiones", categoria = "Pecho"))
        // agregarEjercicio(Ejercicio(nombre = "Sentadillas", repeticionesRecomendadas = 15, imagen = "sentadillas", categoria = "Piernas"))
        listener?.onDataChanged()
    }

    fun agregarEjercicio(ejercicio: Ejercicio) {
        ejercicio.id = nextId++ // Asigna el siguiente ID disponible
        ejerciciosList.add(ejercicio)
        listener?.onDataChanged()
    }

    fun eliminarEjercicio(ejercicio: Ejercicio) {
        ejerciciosList.remove(ejercicio)
        listener?.onDataChanged()
    }

    fun dataCambiada(){
        listener?.onDataChanged()
    }

    fun getAllEjercicios(): List<Ejercicio> {
        return ejerciciosList
    }
}