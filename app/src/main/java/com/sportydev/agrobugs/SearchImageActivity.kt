package com.sportydev.agrobugs

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_image)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura los botones de la barra de navegación inferior
        setupBottomNavigation()
    }

    /**
     * Configura los listeners para la barra de navegación inferior.
     */
    private fun setupBottomNavigation() {
        // Botón de Inicio -> Vuelve a MainActivity
        findViewById<LinearLayout>(R.id.nav_inicio).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Cierra esta actividad para no volver a ella con el botón de atrás
        }

        // Botón de Búsqueda Normal -> SearchManuallyActivity
        findViewById<LinearLayout>(R.id.nav_busqueda_normal).setOnClickListener {
            startActivity(Intent(this, SearchManuallyActivity::class.java))
        }

        // Botón de Cámara (FloatingActionButton) -> No hace nada, ya estamos aquí
        findViewById<FloatingActionButton>(R.id.nav_camera).setOnClickListener {
            Toast.makeText(this, "Ya estás en la búsqueda por imagen", Toast.LENGTH_SHORT).show()
        }

        // Botón Pro -> SearchBinaryActivity
        findViewById<LinearLayout>(R.id.nav_busqueda_pro).setOnClickListener {
            startActivity(Intent(this, SearchBinaryActivity::class.java))
        }

        // Botón de Ajustes
        findViewById<LinearLayout>(R.id.nav_ajustes).setOnClickListener {
            Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show()
        }
    }
}
