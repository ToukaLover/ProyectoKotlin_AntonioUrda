package com.antoniourda.proyectoantonio.models

import com.google.firebase.firestore.DocumentId

data class Ejercicio(
    @DocumentId var id: String = "", // ID generado por Firestore
    var nombre: String = "",
    var repeticionesRecomendadas: Int = 0,
    var imagen: String = "",
    var categoria: String = ""
) {
    constructor() : this("", "", 0, "", "") // Constructor sin parámetros necesario para Firestore
}