package com.sportydev.agrobugs

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupCards()
        setupBottomNavigation()
    }

    /**
     * Configura los listeners para las cards principales.
     */
    private fun setupCards() {
        findViewById<CardView>(R.id.cardIdentificarFoto).setOnClickListener {
            startActivity(Intent(this, SearchImageActivity::class.java))
        }

        findViewById<CardView>(R.id.cardBusquedaBinaria).setOnClickListener {
            startActivity(Intent(this, SearchBinaryActivity::class.java))
        }

        findViewById<CardView>(R.id.cardBusquedaManual).setOnClickListener {
            startActivity(Intent(this, SearchManuallyActivity::class.java))
        }
    }

    /**
     * Configura los listeners para la barra de navegación inferior.
     */
    private fun setupBottomNavigation() {
        // Botón de Inicio -> No hace nada
        findViewById<LinearLayout>(R.id.nav_inicio).setOnClickListener {

        }

        // Botón de Búsqueda Normal -> SearchManuallyActivity
        findViewById<LinearLayout>(R.id.nav_busqueda_normal).setOnClickListener {
            startActivity(Intent(this, SearchManuallyActivity::class.java))
        }

        // Botón de Cámara (FloatingActionButton) -> SearchImageActivity
        findViewById<FloatingActionButton>(R.id.nav_camera).setOnClickListener {
            startActivity(Intent(this, SearchImageActivity::class.java))
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
