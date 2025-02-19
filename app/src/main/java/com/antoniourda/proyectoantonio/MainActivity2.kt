package com.antoniourda.proyectoantonio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniourda.proyectoantonio.adapter.Adapter
import com.antoniourda.proyectoantonio.controller.Controller
import com.antoniourda.proyectoantonio.models.Ejercicio
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var adapter: Adapter
    private lateinit var controller: Controller
    private lateinit var recycler: RecyclerView
    private lateinit var anadir: Button

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Inicializar componentes
        recycler = findViewById(R.id.recycler)
        anadir = findViewById(R.id.botonAnadir)
        controller = Controller(this) // Asegúrate de pasar el contexto

        // Configurar RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(mutableListOf(), { ejercicio ->
            controller.eliminarEjercicio(ejercicio)
        }, { ejercicio ->
            showEditDialog(ejercicio) // Mostrar el diálogo de edición
        })
        recycler.adapter = adapter

        // Cargar ejercicios
        controller.setOnDataChangeListener(object : Controller.OnDataChangeListener {
            override fun onDataChanged() {
                adapter.updateList(controller.getAllEjercicios().toMutableList())
            }
        })
        controller.cargarEjercicios() // Cargar ejercicios al iniciar

        // Botón para agregar ejercicios
        anadir.setOnClickListener {
            val addDialog = addDialog(this, controller, adapter)
            addDialog.show()
        }

        // Configurar Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> startActivity(Intent(this, home::class.java))
                R.id.nav_inicio -> Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show()
                R.id.nav_tips -> startActivity(Intent(this, MainActivity2::class.java))
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showEditDialog(ejercicio: Ejercicio) {
        val editDialog = editDialog(this, ejercicio, controller) // Crear el diálogo para editar
        editDialog.show()
    }
}