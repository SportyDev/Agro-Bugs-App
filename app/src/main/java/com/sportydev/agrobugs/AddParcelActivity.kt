package com.sportydev.agrobugs

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class AddParcelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_parcel)

        // 1. Configurar el Dropdown de Cultivos
        val cultivos = listOf("Maíz", "Sorgo", "Trigo", "Frijol", "Otro")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cultivos)
        val etCropType = findViewById<AutoCompleteTextView>(R.id.etCropType)
        etCropType.setAdapter(adapter)
        // 2. Botón Cancelar / Atrás
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // 2. Configurar el Selector de Fecha (DatePicker)
        val etDate = findViewById<TextInputEditText>(R.id.etDate)
        etDate.setOnClickListener {
            mostrarCalendario(etDate)
        }

        // 3. Botón Guardar
        val btnSave = findViewById<Button>(R.id.btnSaveParcel)
        btnSave.setOnClickListener {
            // Aquí iría tu lógica para guardar en Room Database
            // Por ahora solo validamos que no esté vacío el nombre
            val etName = findViewById<TextInputEditText>(R.id.etName)

            if (etName.text.isNullOrEmpty()) {
                etName.error = "Debes ponerle un nombre"
            } else {
                Toast.makeText(this, "Parcela guardada exitosamente", Toast.LENGTH_SHORT).show()
                finish() // Cierra la pantalla y vuelve al Dashboard
            }
        }

        // 4. Botón Atrás
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // 5. Botón Mapa (Placeholder)
        findViewById<Button>(R.id.btnSetLocation).setOnClickListener {
            Toast.makeText(this, "Aquí abrirías Google Maps", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarCalendario(campoTexto: TextInputEditText) {
        val calendario = Calendar.getInstance()
        val año = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            // El mes empieza en 0, así que le sumamos 1
            val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
            campoTexto.setText(fechaSeleccionada)
        }, año, mes, dia)

        datePicker.show()
    }
}