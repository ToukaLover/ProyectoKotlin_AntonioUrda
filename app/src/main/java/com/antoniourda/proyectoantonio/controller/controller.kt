package com.antoniourda.proyectoantonio.controller

import android.content.Context
import com.antoniourda.proyectoantonio.adapter.Adapter
import com.antoniourda.proyectoantonio.addDialog
import com.antoniourda.proyectoantonio.editDialog
import com.antoniourda.proyectoantonio.models.Ejercicio

class Controller(val context: Context){

    private var ejercicios = mutableListOf(
        Ejercicio("Press Militar",8,"press_militar","hombro"),
        Ejercicio("Jalon al Pecho",12,"jalon_pecho","espalda"),
        Ejercicio("Extension de Cuadriceps",12,"ext_cuadriceps","pierna"),
        Ejercicio("Elevaciones Laterales",10,"laterales","hombro"),
        Ejercicio("Press de banca",6,"banca","pecho"),
        Ejercicio("Curl de Biceps",10,"biceps","brazo"),
        Ejercicio("Extension de Triceps",12,"triceps","brazo"),
        Ejercicio("Peso Muerto",4,"peso_muerto","pierna"),
        Ejercicio("Sentadilla",8,"sentadilla","pierna"),
        Ejercicio("Al fallo                ",1000,"fallo","si")
    )

//    private var ejerciciosPierna = ejercicios.filter {  }

    private var onDataChangeListener: OnDataChangeListener? = null

    fun addEjercicio(ejercicio: Ejercicio) {
        ejercicios.add(ejercicio)
        onDataChangeListener?.onDataChanged()
    }
    fun dataCambiada(){
        onDataChangeListener?.onDataChanged()
    }
    fun deleteEjercicio(ejercicio: Ejercicio) {
        var position = ejercicios.indexOf(ejercicio)
        ejercicios.removeAt(position)
        onDataChangeListener?.onDataChanged()
    }
    fun getAllEjercicios(): MutableList<Ejercicio> = ejercicios

    fun setOnDataChangeListener(listener: OnDataChangeListener) {
        onDataChangeListener = listener
    }
    fun showDialog(ejercicio: Ejercicio){
        val position = ejercicios.indexOf(ejercicio)
        val dialog: editDialog
        dialog = editDialog(context,ejercicio,ejercicios,position,this)
        dialog.show()
    }
    interface OnDataChangeListener {
        fun onDataChanged()
    }

}
