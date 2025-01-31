package com.antoniourda.proyectoantonio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val olvido = findViewById<TextView>(R.id.olvido)
        val btnLogin = findViewById<Button>(R.id.iniciarSesion)
        var correo = findViewById<TextInputLayout>(R.id.nombreUsuario1)
        var pass = findViewById<TextInputLayout>(R.id.password1)
        val btnRegister = findViewById<Button>(R.id.register)

        olvido.setOnClickListener{

            if(correo.editText?.text.toString()!=""){
                if(Patterns.EMAIL_ADDRESS.matcher(correo.editText?.text.toString()).matches()){
                    resetPassword(correo.editText?.text.toString())
                }else{
                    Toast.makeText(this, "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Introduce un correo", Toast.LENGTH_LONG).show()
            }

        }

        btnLogin.setOnClickListener {
            if(pass.editText?.text.toString()!=""){
                if(correo.editText?.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.editText?.text.toString()).matches()){
                    login_firebase(correo.editText?.text.toString(), pass.editText?.text.toString())
                    login_firebase(correo.editText?.text.toString(), pass.editText?.text.toString())
                    login_firebase(correo.editText?.text.toString(), pass.editText?.text.toString())
                }else{
                    Log.d("TAG", "correo: "+correo)
                    Toast.makeText(this, "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Introduce contraseña", Toast.LENGTH_LONG).show()
            }

        }

        btnRegister.setOnClickListener {
            RegisterDialog().show(supportFragmentManager, null)
        }
    }

    fun login_firebase(correo: String, pass: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, home::class.java)
                    intent.putExtra("Correo", correo)
                    intent.putExtra("Proveedor", "Usuario/Contraseña")
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Usuario/Contraseña incorrecto(s)", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun resetPassword(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de recuperación enviado.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Correo no existente", Toast.LENGTH_LONG).show()
                }
            }
    }
}