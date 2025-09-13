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

class SearchManuallyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_search_manually)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura los listeners para las tarjetas y la barra de navegación
        setupCardClickListeners()
        setupBottomNavigation()
    }

    /**
     * Configura los listeners para cada CardView de plaga.
     */
    private fun setupCardClickListeners() {
        findViewById<CardView>(R.id.cardMoscaBlanca).setOnClickListener { navigateToBugInfo("Mosca blanca") }
        findViewById<CardView>(R.id.cardPulgon).setOnClickListener { navigateToBugInfo("Pulgón") }
        findViewById<CardView>(R.id.cardTrips).setOnClickListener { navigateToBugInfo("Trips") }
        findViewById<CardView>(R.id.cardAranaRoja).setOnClickListener { navigateToBugInfo("Araña roja") }
        findViewById<CardView>(R.id.cardMinador).setOnClickListener { navigateToBugInfo("Minador de hojas") }
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
            finish() // Cierra esta actividad
        }

        // Botón de Búsqueda Normal -> No hace nada
        findViewById<LinearLayout>(R.id.nav_busqueda_normal).setOnClickListener {
            Toast.makeText(this, "Ya estás en la búsqueda manual", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Configuración - Próximamente", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Navega a InformationBugActivity pasando el nombre de la plaga.
     */
    private fun navigateToBugInfo(bugName: String) {
        val intent = Intent(this, InformationBugActivity::class.java)
        intent.putExtra("BUG_NAME", bugName)
        startActivity(intent)
    }
}
