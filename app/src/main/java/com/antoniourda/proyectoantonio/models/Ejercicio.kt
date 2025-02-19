package com.antoniourda.proyectoantonio.models

data class Ejercicio(
    var id: Int = 0, // ID autoincremental
    var nombre: String = "",
    var repeticionesRecomendadas: Int = 0,
    var imagen: String = "",
    var categoria: String = ""
) {
    constructor() : this(0, "", 0, "", "") // Constructor sin parámetros
}