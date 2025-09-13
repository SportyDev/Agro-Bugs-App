package com.sportydev.agrobugs

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InformationBugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_bug)


        // Configura las pestañas y el ViewPager
        setupTabs()

        // Configura los botones de la barra de navegación inferior
        setupBottomNavigation()
    }

    /**
     * Configura el ViewPager2 y el TabLayout para mostrar la información en pestañas.
     */
    private fun setupTabs() {
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val pagerAdapter = InfoPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Descripción"
                1 -> "Daños"
                2 -> "Control"
                else -> null
            }
        }.attach()
    }

    /**
     * Configura los listeners para la barra de navegación inferior.
     */
    private fun setupBottomNavigation() {
        // Botón de Inicio -> Vuelve a MainActivity
        findViewById<LinearLayout>(R.id.nav_inicio).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Limpia las actividades anteriores para no crear un bucle
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Cierra esta actividad
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
