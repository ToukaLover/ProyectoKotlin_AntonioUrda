package com.antoniourda.proyectoantonio

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
/*
import androidx.appcompat.widget.ActionBarDrawerToggle
*/
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.antoniourda.proyectoantonio.MainActivity2
import com.antoniourda.proyectoantonio.R
import com.google.android.material.navigation.NavigationView

class home : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


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
                    // Acción para el Home
                    Toast.makeText(this, "Ya estás en Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_inicio -> {
                    // Navegar a InicioActivity
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
                R.id.nav_tips -> {
                    // Navegar a Tips
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
                R.id.nav_cerrarsesion ->{
                    borrar_sesion()
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

    fun borrar_sesion() {
        val prefs = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        // Redirigir al usuario a la pantalla de inicio de sesión
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Evita que el usuario vuelva atrás
        startActivity(intent)
        finish()
    }

}
