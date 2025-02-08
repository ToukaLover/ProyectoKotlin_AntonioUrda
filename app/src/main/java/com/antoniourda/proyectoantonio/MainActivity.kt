package com.antoniourda.proyectoantonio

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "sharedpreferences"
        private const val KEY_CORREO = "Correo"
        private const val KEY_PROVEEDOR = "Proveedor"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si hay una sesión guardada ANTES de cargar la UI
        if (verificarSesion()) {
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val olvido = findViewById<TextView>(R.id.olvido)
        val btnLogin = findViewById<Button>(R.id.iniciarSesion)
        val correo = findViewById<TextInputLayout>(R.id.nombreUsuario1)
        val pass = findViewById<TextInputLayout>(R.id.password1)
        val btnRegister = findViewById<Button>(R.id.register)

        olvido.setOnClickListener {
            val emailText = correo.editText?.text.toString()
            if (emailText.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                resetPassword(emailText)
            } else {
                Toast.makeText(this, "Introduce un correo válido", Toast.LENGTH_LONG).show()
            }
        }

        btnLogin.setOnClickListener {
            val emailText = correo.editText?.text.toString()
            val passText = pass.editText?.text.toString()

            if (emailText.isNotEmpty() && passText.isNotEmpty()) {
                loginFirebase(emailText, passText)
            } else {
                Toast.makeText(this, "Introduce correo y contraseña", Toast.LENGTH_LONG).show()
            }
        }

        btnRegister.setOnClickListener {
            RegisterDialog().show(supportFragmentManager, null)
        }
    }

    private fun loginFirebase(correo: String, pass: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    guardarSesion(correo, "Usuario/Contraseña")
                    irAHome(correo, "Usuario/Contraseña")
                } else {
                    Toast.makeText(this, "Usuario/Contraseña incorrecto(s)", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun resetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                val message = if (task.isSuccessful) "Correo de recuperación enviado." else "Correo no existente"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
    }

    private fun guardarSesion(correo: String, proveedor: String) {
        val prefs: SharedPreferences.Editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        prefs.putString(KEY_CORREO, correo)
        prefs.putString(KEY_PROVEEDOR, proveedor)
        prefs.apply()
    }

    private fun verificarSesion(): Boolean {
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val correo = prefs.getString(KEY_CORREO, null)
        val proveedor = prefs.getString(KEY_PROVEEDOR, null)

        return if (correo != null && proveedor != null) {
            irAHome(correo, proveedor)
            true
        } else {
            false
        }
    }

    private fun irAHome(correo: String, proveedor: String) {
        val intent = Intent(this, home::class.java).apply {
            putExtra("Correo", correo)
            putExtra("Proveedor", proveedor)
        }
        startActivity(intent)
        finish()
    }
}