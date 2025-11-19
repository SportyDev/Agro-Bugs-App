package com.sportydev.agrobugs

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MonitoringDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_monitoring_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Botón ATRÁS (Flecha superior)
        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener {
            finish() // Cierra esta actividad y vuelve al Main
        }

        // 2. Botón AGREGAR PARCELA (Tarjeta con el signo +)
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardAddLote).setOnClickListener {
            val intent = Intent(this, AddParcelActivity::class.java)
            startActivity(intent)
        }

        // 3. VER DETALLES DE UNA PARCELA (Tarjeta "Parcela Norte")
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardLote1).setOnClickListener {
            val intent = Intent(this, ParcelDetailActivity::class.java)
            // Le pasamos el nombre para que la siguiente pantalla sepa qué mostrar
            intent.putExtra("PARCEL_NAME", "Parcela Norte")
            startActivity(intent)
        }
    }
}