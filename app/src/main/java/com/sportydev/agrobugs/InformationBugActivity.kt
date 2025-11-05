package com.sportydev.agrobugs

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView

class InformationBugActivity : BaseActivity() {

    companion object {
        const val EXTRA_PLAGA_ID = "extra_plaga_id"
    }

    // Vistas principales
    private lateinit var tvPestName: TextView
    private lateinit var tvScientificName: TextView
    private lateinit var tvPestDescription: TextView

    // Vistas para el header de imágenes
    private lateinit var ivMainImage: ShapeableImageView
    private lateinit var ivSubImage1: ShapeableImageView
    private lateinit var ivSubImage2: ShapeableImageView

    // Vistas para el Bottom Sheet
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var tvSheetTitle: TextView
    private lateinit var tvSheetContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_information_bug)

        tvPestName = findViewById(R.id.tvBugName)
        tvScientificName = findViewById(R.id.tvScientificName)
        tvPestDescription = findViewById(R.id.tvBugDescription)
        ivMainImage = findViewById(R.id.ivMainImage)
        ivSubImage1 = findViewById(R.id.ivSubImage1)
        ivSubImage2 = findViewById(R.id.ivSubImage2)

        val pestId = intent.getIntExtra(EXTRA_PLAGA_ID, -1)

        if (pestId != -1) {
            val dbHelper = AdminBd(this)
            val pest = dbHelper.getPestById(pestId)

            if (pest != null) {
                populateUI(pest)
            } else {
                Toast.makeText(this, "Error: No se encontró la plaga.", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Error: No se recibió un ID válido", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun populateUI(pest: Plaga) {
        tvPestName.text = pest.nomPlaga
        tvScientificName.text = pest.nomcientifico
        tvPestDescription.text = pest.descPlaga

        setupImageHeader(pest.imageName)
        setupViewsAndListeners(pest)
    }

    private fun setupImageHeader(imageNames: List<String>) {
        val validImageNames = imageNames.filter { it.isNotBlank() }
        val defaultImage = "img_question"

        setImage(ivMainImage, validImageNames.getOrNull(0) ?: defaultImage)
        setImage(ivSubImage1, validImageNames.getOrNull(1) ?: defaultImage)
        setImage(ivSubImage2, validImageNames.getOrNull(2) ?: defaultImage)
    }

    private fun setImage(imageView: ImageView, imageName: String) {
        val imageId = resources.getIdentifier(imageName.trim(), "drawable", packageName)
        if (imageId != 0) {
            imageView.setImageResource(imageId)
        } else {
            imageView.setImageResource(R.drawable.img_question)
        }
    }

    private fun setupViewsAndListeners(pest: Plaga) {
        findViewById<MaterialCardView>(R.id.btnBack).setOnClickListener { finish() }

        val bottomSheetCard: MaterialCardView = findViewById(R.id.bottomSheetCard)
        tvSheetTitle = findViewById(R.id.tvSheetTitle)
        tvSheetContent = findViewById(R.id.tvSheetContent)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetCard)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        findViewById<ImageButton>(R.id.btnCloseSheet).setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        // --- Configuración de Items CON ICONOS ---
        setupInfoItem(R.id.itemDamage, R.drawable.ic_damage, "Daños", {
            showBottomSheetWithContent("Daños a los Cultivos", pest.damage)
        })
        setupInfoItem(R.id.itemSampling, R.drawable.ic_sampling, "Muestreo", {
            showBottomSheetWithContent("Cómo Realizar el Muestreo", pest.muestreo)
        })
        setupInfoItem(R.id.itemControlBiological, R.drawable.ic_biological, "Control Biológico", {
            showBottomSheetWithContent("Control Biológico", pest.biolControl)
        })
        setupInfoItem(R.id.itemControlCultural, R.drawable.ic_cultural, "Control Cultural", {
            showBottomSheetWithContent("Control Cultural", pest.cultControl)
        })
        setupInfoItem(R.id.itemControlEthological, R.drawable.ic_ethological, "Control Etológico", {
            showBottomSheetWithContent("Control Etológico", pest.etolControl)
        })
        setupInfoItem(R.id.itemControlChemical, R.drawable.ic_chemical, "Control Químico", {
            showBottomSheetWithContent("Control Químico", pest.quimControl)
        })
        setupInfoItem(R.id.itemResistance, R.drawable.ic_resistance, "Manejo de Resistencia", {
            showBottomSheetWithContent("Manejo de Resistencia", pest.resistManejo)
        })
    }

    private fun showBottomSheetWithContent(title: String, content: String) {
        tvSheetTitle.text = title
        tvSheetContent.text = content
        findViewById<ImageView>(R.id.ivSheetImage).visibility = View.GONE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupInfoItem(itemId: Int, iconRes: Int, title: String, onClickAction: () -> Unit) {
        val itemLayout: View = findViewById(itemId)
        val ivIcon: ImageView = itemLayout.findViewById(R.id.ivIcon)
        val tvTitle: TextView = itemLayout.findViewById(R.id.tvTitle)

        ivIcon.setImageResource(iconRes)
        tvTitle.text = title

        itemLayout.setOnClickListener { onClickAction() }
    }
}