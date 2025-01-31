package com.antoniourda.proyectoantonio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val recycler : RecyclerView = findViewById(R.id.recycler)

        controller = Controller(this)

            /*controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()
            controller.addEjercicio()*/

        recycler.layoutManager = LinearLayoutManager(this)
        val anadir : Button = findViewById(R.id.botonAnadir)
        // Inicializar el adaptador con los datos del controlador
        adapter = Adapter(controller.getAllEjercicios(),  { ejercicio ->
            controller.deleteEjercicio(ejercicio)
        }, {
            ejercicio ->  controller.showDialog(ejercicio)
        })
        controller.setOnDataChangeListener(object : Controller.OnDataChangeListener {
            override fun onDataChanged() {
                adapter.updateList(controller.getAllEjercicios().toMutableList())
            }
        })
        recycler.adapter = adapter
        anadir.setOnClickListener{
            val addDialog : addDialog
            addDialog = addDialog(this,controller,adapter)
            addDialog.show()
            adapter.notifyItemInserted(adapter.itemCount)
        }

        // Ponemos el menu
        // Vincular los elementos del diseño
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        // Configura el ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        // Agregar listener al DrawerLayout
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Habilitar el botón de menú en la barra de acciones
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configurar listener del Navigation Drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, home::class.java)
                    startActivity(intent)

                }
                R.id.nav_inicio -> {
                    // Acción para el Home
                    Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_tips -> {
                    // Navegar a InicioActivity
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers() // Cierra el drawer después de seleccionar una opción
            true
        }
    }

    override fun onBackPressed() {
        // Cierra el drawer si está abierto; de lo contrario, usa el comportamiento predeterminado
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        // Maneja el evento del botón del menú
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}