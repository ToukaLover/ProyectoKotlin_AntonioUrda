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
import kotlin.contracts.contract

class addDialog(context: Context,
                var controller: Controller,
                var adapter: Adapter
) : Dialog(context) {
    private lateinit var nameEditText: EditText
    private lateinit var repes: EditText
    private lateinit var imagen: EditText
    private lateinit var categoria: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)

        nameEditText = findViewById(R.id.profile_name)
        repes = findViewById(R.id.repes)
        imagen = findViewById(R.id.imagen)
        categoria = findViewById(R.id.categoria)

        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        saveButton.setOnClickListener {

            val nombre = nameEditText.text.toString().trim()
            val repes = repes.text.toString().trim().toIntOrNull() ?: 0
            val imagen = imagen.text.toString().trim()
            val categoria = categoria.text.toString().trim()

            if (nombre.isNotEmpty() && imagen.isNotEmpty()) {
                val nuevoEjercicio = Ejercicio(nombre, repes, imagen,categoria)

                controller.addEjercicio(nuevoEjercicio) // Agrega al Controller
                dismiss() // Cierra el diálogo
            }
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}