package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.data.local.UserEntity
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el repositorio
        userRepo = UserRepository(this)

        // Pre-pobla admin/1234 si no existe
        lifecycleScope.launch {
            val existing = userRepo.login("admin", "1234")
            if (existing == null) {
                userRepo.register(
                    UserEntity(
                        username = "admin",
                        password = "1234"
                    )
                )
            }
        }

        // Botón Entrar
        binding.btnLogin.setOnClickListener {
            val usr = binding.etUsername.text.toString().trim()
            val pwd = binding.etPassword.text.toString().trim()
            if (usr.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Usuario y contraseña obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val loggedIn = userRepo.login(usr, pwd)
                runOnUiThread {
                    if (loggedIn != null) {
                        // Lanzar MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Usuario o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
