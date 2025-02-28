package com.antoniourda.proyectoantonio

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recycler = findViewById(R.id.recycler)
        anadir = findViewById(R.id.botonAnadir)
        controller = Controller(this)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(mutableListOf(), { ejercicio ->
            controller.eliminarEjercicio(ejercicio)
        }, { ejercicio ->
            showEditDialog(ejercicio)
        })
        recycler.adapter = adapter

        controller.setOnDataChangeListener(object : Controller.OnDataChangeListener {
            override fun onDataChanged() {
                adapter.updateList(controller.getAllEjercicios().toMutableList())
            }
        })
        controller.cargarEjerciciosDesdeFirebase()

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

        // Habilitar el botón de menú en la barra de acciones
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> startActivity(Intent(this, home::class.java))
                R.id.nav_inicio -> Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show()
                R.id.nav_cerrarsesion -> borrarSesion()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showEditDialog(ejercicio: Ejercicio) {
        val editDialog = editDialog(this, ejercicio, controller)
        editDialog.show()
    }

    private fun borrarSesion() {
        val prefs = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    // Manejar la apertura del menú lateral con el botón de hamburguesa
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Manejar el botón "atrás" para cerrar el menú si está abierto
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
