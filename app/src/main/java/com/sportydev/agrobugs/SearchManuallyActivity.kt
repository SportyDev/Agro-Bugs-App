package com.sportydev.agrobugs

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// 1. AÑADIMOS LA INTERFAZ DEL ADAPTADOR AQUÍ
class SearchManuallyActivity : BaseActivity(), PlagaAdapter.OnItemClickListener {

    private lateinit var pestsRecyclerView: RecyclerView
    private lateinit var pestAdapter: PlagaAdapter
    private var pestList = listOf<Plaga>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_manually)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pestsRecyclerView = findViewById(R.id.pestsRecyclerView)
        pestsRecyclerView.layoutManager = LinearLayoutManager(this)

        loadPestsFromDb()
//        Toast.makeText(this, "Plagas encontradas: ${pestList.size}", Toast.LENGTH_LONG).show()

        pestAdapter = PlagaAdapter(this, pestList, this)
        pestsRecyclerView.adapter = pestAdapter

        setupBottomNavigation()
    }

    override fun onItemClick(pest: Plaga) {
        val intent = Intent(this, InformationBugActivity::class.java).apply {
            putExtra(InformationBugActivity.EXTRA_PLAGA_ID, pest.idPlaga)
        }
        startActivity(intent)
    }

    private fun loadPestsFromDb() {
        val dbHelper = AdminBd(this)
        try {
            pestList = dbHelper.getAllPests()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupBottomNavigation() {
        findViewById<LinearLayout>(R.id.nav_inicio).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        findViewById<LinearLayout>(R.id.nav_busqueda_normal).setOnClickListener {
        }
        findViewById<FloatingActionButton>(R.id.nav_camera).setOnClickListener {
            startActivity(Intent(this, SearchImageActivity::class.java))
            overridePendingTransition(0, 0)
        }
        findViewById<LinearLayout>(R.id.nav_busqueda_pro).setOnClickListener {
            startActivity(Intent(this, SearchBinaryActivity::class.java))
            overridePendingTransition(0, 0)
        }
        findViewById<LinearLayout>(R.id.nav_ajustes).setOnClickListener {
            startActivity(Intent(this, ConfigurationActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }


}