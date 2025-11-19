package com.sportydev.agrobugs

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ParcelDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_parcel_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperamos el nombre que nos mandaron desde el Dashboard
        val parcelName = intent.getStringExtra("PARCEL_NAME") ?: "Mi Parcela"
        findViewById<android.widget.TextView>(R.id.tvParcelName).text = parcelName

        // 1. Botón REGISTRAR NUEVO MONITOREO
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.btnStartMonitoring).setOnClickListener {
            val intent = Intent(this, MonitoringSetupActivity::class.java)
            // Le pasamos el nombre del lote a la configuración
            intent.putExtra("PARCEL_NAME", parcelName)
            startActivity(intent)
        }

        // 2. Botón Atrás
        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}