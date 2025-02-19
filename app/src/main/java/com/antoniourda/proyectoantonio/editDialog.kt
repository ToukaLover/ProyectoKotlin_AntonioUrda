package com.antoniourda.proyectoantonio

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.antoniourda.proyectoantonio.controller.Controller
import com.antoniourda.proyectoantonio.models.Ejercicio

class editDialog(
    context: Context,
    private var ejercicio: Ejercicio,
    private var controller: Controller
) : Dialog(context) {

    private lateinit var nameEditText: EditText
    private lateinit var repesEditText: EditText
    private lateinit var imagenEditText: EditText
    private lateinit var categoriaEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)

        // Inicializar campos de entrada
        nameEditText = findViewById(R.id.profile_name)
        nameEditText.setText(ejercicio.nombre)

        repesEditText = findViewById(R.id.repes)
        repesEditText.setText(ejercicio.repeticionesRecomendadas.toString())

        imagenEditText = findViewById(R.id.imagen)
        imagenEditText.setText(ejercicio.imagen)

        categoriaEditText = findViewById(R.id.categoria)
        categoriaEditText.setText(ejercicio.categoria)

        // Inicializar botones
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        // Listener para el botón de guardar
        saveButton.setOnClickListener {
            val updatedName = nameEditText.text.toString().trim()
            val updatedReps = repesEditText.text.toString().trim()
            val updatedImg = imagenEditText.text.toString().trim()
            val updatedCategoria = categoriaEditText.text.toString().trim()

            // Validación de entradas
            if (updatedName.isEmpty() || updatedReps.isEmpty()) {
                if(updatedName.isEmpty()){
                    nameEditText.error = "Nombre no puede estar vacío"
                }
                if(updatedReps.isEmpty()){
                    repesEditText.error = "Repeticiones no pueden estar vacías"
                }
                return@setOnClickListener
            }

            // Actualizar el ejercicio existente
            ejercicio.nombre = updatedName
            ejercicio.repeticionesRecomendadas = updatedReps.toInt()
            ejercicio.imagen = updatedImg
            ejercicio.categoria = updatedCategoria

            // Notificar al controlador que los datos han cambiado
            controller.dataCambiada()
            dismiss() // Cierra el diálogo
        }

        // Listener para el botón de cancelar
        cancelButton.setOnClickListener {
            dismiss() // Cierra el diálogo
        }
    }
}