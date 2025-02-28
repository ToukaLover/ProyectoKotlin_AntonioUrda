package com.antoniourda.proyectoantonio

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
/*
import androidx.appcompat.widget.ActionBarDrawerToggle
*/
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.antoniourda.proyectoantonio.MainActivity2
import com.antoniourda.proyectoantonio.R
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView

class home : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView
    private lateinit var btnPlayVideo: Button
    private lateinit var btnPlayAudio: Button
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configurar el DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        imageView = findViewById(R.id.imageView)
        videoView = findViewById(R.id.videoView)
        btnPlayAudio = findViewById(R.id.btnPlayAudio)

        // Configurar el ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Manejar clics en los ítems del menú
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> Toast.makeText(this, "Ya estás en Home", Toast.LENGTH_SHORT).show()
                R.id.nav_inicio -> startActivity(Intent(this, MainActivity2::class.java))
                R.id.nav_cerrarsesion -> borrarSesion()
            }
            drawerLayout.closeDrawers()
            true
        }

        // Cargar imagen usando Glide
        Glide.with(this)
            .load("https://www.menzig.fit/images/a/2000/2990-h1.jpg")
            .into(imageView)

        // Configurar el VideoView con el archivo de video en la carpeta raw
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.video2)
        videoView.setVideoURI(videoUri)
        videoView.start()

        // Configurar el botón para reproducir el audio
        btnPlayAudio.setOnClickListener {
            if (::mediaPlayer.isInitialized) {
                mediaPlayer.release()
            }
            mediaPlayer = MediaPlayer.create(this, R.raw.yea_buddy)
            mediaPlayer.start()
        }
    }

    override fun onBackPressed() {
        // Cierra el menú si está abierto, si no, usa la acción normal
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        // Permitir que el botón de menú abra el Drawer
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun borrarSesion() {
        val prefs = getSharedPreferences("sharedpreferences", MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
