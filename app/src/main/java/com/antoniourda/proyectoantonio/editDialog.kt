package com.antoniourda.proyectoantonio

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.antoniourda.proyectoantonio.adapter.Adapter
import com.antoniourda.proyectoantonio.controller.Controller
import com.antoniourda.proyectoantonio.models.Ejercicio

class editDialog(context: Context,
                 private var ejercicio: Ejercicio,
                 private val ejercicios : MutableList<Ejercicio>,
                 private var position : Int,
                var controller: Controller
) : Dialog(context) {
    private lateinit var nameEditText: EditText
    private lateinit var repes: EditText
    private lateinit var imagen:EditText

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)

        nameEditText = findViewById(R.id.profile_name)
        nameEditText.setText(ejercicio.nombre)

        repes = findViewById(R.id.repes)
        repes.setText(ejercicio.repeticionesRecomendadas.toString())

        imagen = findViewById(R.id.imagen)
        imagen.setText(ejercicio.imagen)

        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        saveButton.setOnClickListener {
            val updatedName = nameEditText.text.toString().trim()
            val updatedReps = repes.text.toString().trim()
            val updatedImg = imagen.text.toString().trim()
            if (updatedName.isEmpty() || updatedReps.isEmpty()) {
                nameEditText.error = "Nombre no puede estar vacío"
                repes.error = "Tagline no puede estar vacío"
                return@setOnClickListener
            }
            var newEjercicio = Ejercicio(updatedName,updatedReps.toInt(),updatedImg,"")
            ejercicios[position] = newEjercicio
            controller.dataCambiada()
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}