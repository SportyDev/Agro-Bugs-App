package com.sportydev.agrobugs

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MonitoringSetupActivity : AppCompatActivity() {

    // Variables para controlar la selección
    private var selectedPattern = "CINCO_OROS" // Valor por defecto

    // Referencias a vistas
    private lateinit var card5: MaterialCardView
    private lateinit var cardZigZag: MaterialCardView
    private lateinit var tvDesc: TextView
    private lateinit var ivPreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoring_setup)

        // 1. Recibir nombre de la parcela
        val parcelName = intent.getStringExtra("PARCEL_NAME") ?: "Parcela"
        findViewById<TextView>(R.id.tvParcelNameSummary).text = parcelName

        // 2. Inicializar vistas
        card5 = findViewById(R.id.cardPattern5)
        cardZigZag = findViewById(R.id.cardPatternZigZag)
        tvDesc = findViewById(R.id.tvPatternDesc)
        ivPreview = findViewById(R.id.ivMapPreviewOverlay)
        val btnStart = findViewById<MaterialButton>(R.id.btnStartSession)

        // 3. Configurar Clics en las Tarjetas de Patrón
        card5.setOnClickListener {
            seleccionarPatron("CINCO_OROS")
        }

        cardZigZag.setOnClickListener {
            seleccionarPatron("ZIGZAG")
        }

        // 4. Configurar Botón Comenzar
        btnStart.setOnClickListener {
            // Aquí vas a la pantalla del Mapa Interactivo (la que tiene los puntos 1,2,3,4,5)
            Toast.makeText(this, "Iniciando patrón: $selectedPattern", Toast.LENGTH_SHORT).show()

            // val intent = Intent(this, ActiveSessionActivity::class.java)
            // intent.putExtra("PATTERN", selectedPattern)
            // startActivity(intent)
        }

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }

        // Estado inicial
        seleccionarPatron("CINCO_OROS")

        findViewById<android.widget.TextView>(R.id.tvParcelNameSummary).text = parcelName

        // ... (tu lógica de selección de 5 Oros / Zigzag) ...

        // 1. Botón COMENZAR RECORRIDO
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnStartSession).setOnClickListener {
            // AQUÍ ES DONDE INICIA LA MAGIA DEL MAPA
            // Por ahora mostramos un mensaje
            android.widget.Toast.makeText(this, "Cargando Mapa y GPS...", android.widget.Toast.LENGTH_SHORT).show()

            // Cuando tengas la Activity del Mapa (ActiveSamplingActivity), descomenta esto:
            // val intent = Intent(this, ActiveSamplingActivity::class.java)
            // intent.putExtra("PATTERN", selectedPattern) // "CINCO_OROS" o "ZIGZAG"
            // startActivity(intent)
        }

        // 2. Botón Atrás
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun seleccionarPatron(patron: String) {
        selectedPattern = patron

        val colorSelectedBg = Color.parseColor("#E8F5E9") // Verde clarito
        val colorSelectedStroke = Color.parseColor("#2E7D32") // Verde fuerte
        val colorUnselectedBg = Color.WHITE
        val colorUnselectedStroke = Color.parseColor("#E0E0E0") // Gris

        if (patron == "CINCO_OROS") {
            // Activar Card 5
            card5.setCardBackgroundColor(colorSelectedBg)
            card5.strokeWidth = 4 // Más grueso
            card5.strokeColor = colorSelectedStroke

            // Desactivar Card ZigZag
            cardZigZag.setCardBackgroundColor(colorUnselectedBg)
            cardZigZag.strokeWidth = 2
            cardZigZag.strokeColor = colorUnselectedStroke

            // Actualizar Textos e Imagen
            tvDesc.text = "Ideal para cultivos uniformes. Muestrea las 4 esquinas y el centro."
            ivPreview.setImageResource(R.drawable.ic_5_oros) // Cambia el icono del mapa

        } else {
            // Activar Card ZigZag
            cardZigZag.setCardBackgroundColor(colorSelectedBg)
            cardZigZag.strokeWidth = 4
            cardZigZag.strokeColor = colorSelectedStroke

            // Desactivar Card 5
            card5.setCardBackgroundColor(colorUnselectedBg)
            card5.strokeWidth = 2
            card5.strokeColor = colorUnselectedStroke

            // Actualizar Textos e Imagen
            tvDesc.text = "Ideal para detectar focos de infección o manchas irregulares en el lote."
            ivPreview.setImageResource(R.drawable.ic_zigzag)
        }
    }
}