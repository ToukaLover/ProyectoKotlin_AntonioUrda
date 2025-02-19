package com.antoniourda.proyectoantonio.controller

import android.content.Context
import android.util.Log
import com.antoniourda.proyectoantonio.models.Ejercicio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Controller(private val context: Context) {
    private val db: FirebaseFirestore = Firebase.firestore
    private val ejerciciosList = mutableListOf<Ejercicio>()

    interface OnDataChangeListener {
        fun onDataChanged()
    }

    private var listener: OnDataChangeListener? = null

    fun setOnDataChangeListener(listener: OnDataChangeListener) {
        this.listener = listener
    }

    fun cargarEjerciciosDesdeFirebase() {
        db.collection("ejercicios")
            .orderBy("nombre") // Ordena por nombre, puedes cambiarlo
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Controller", "Error al cargar ejercicios", error)
                    return@addSnapshotListener
                }

                snapshot?.let {
                    ejerciciosList.clear()
                    for (document in it.documents) {
                        val ejercicio = document.toObject(Ejercicio::class.java)
                        ejercicio?.let { e -> ejerciciosList.add(e) }
                    }
                    listener?.onDataChanged()
                }
            }
    }

    fun agregarEjercicio(ejercicio: Ejercicio) {
        db.collection("ejercicios").add(ejercicio)
            .addOnSuccessListener { documentReference ->
                Log.d("Controller", "Ejercicio agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Controller", "Error al agregar ejercicio", e)
            }
    }

    fun eliminarEjercicio(ejercicio: Ejercicio) {
        db.collection("ejercicios").document(ejercicio.id).delete()
            .addOnSuccessListener {
                Log.d("Controller", "Ejercicio eliminado con ID: ${ejercicio.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Controller", "Error al eliminar ejercicio", e)
            }
    }
    fun editarEjercicio(ejercicio: Ejercicio) {
        db.collection("ejercicios").document(ejercicio.id)
            .set(ejercicio)
            .addOnSuccessListener {
                Log.d("Controller", "Ejercicio editado con ID: ${ejercicio.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Controller", "Error al editar ejercicio", e)
            }
    }

    fun dataCambiada(){
        listener?.onDataChanged()
    }

    fun getAllEjercicios(): List<Ejercicio> {
        return ejerciciosList
    }
}