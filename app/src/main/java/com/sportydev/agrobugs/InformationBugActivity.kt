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
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class InformationBugActivity : BaseActivity() {

    // Vistas para el Bottom Sheet
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var tvSheetTitle: TextView
    private lateinit var tvSheetContent: TextView
    private lateinit var ivSheetImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_information_bug)

        setupViewsAndListeners()
    }

    private fun setupViewsAndListeners() {
        // --- Botón de Retroceso ---
        val btnBack: MaterialCardView = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }
        // --- Botón Inferior ---
        val btnSavePest: MaterialButton = findViewById(R.id.btnSavePest)
        btnSavePest.setOnClickListener {
            Toast.makeText(this, "Plaga guardada en tu lista", Toast.LENGTH_SHORT).show()
        }

        // --- Configuración del Bottom Sheet ---
        val bottomSheetCard: MaterialCardView = findViewById(R.id.bottomSheetCard)
        tvSheetTitle = findViewById(R.id.tvSheetTitle)
        tvSheetContent = findViewById(R.id.tvSheetContent)
        ivSheetImage = findViewById(R.id.ivSheetImage)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetCard)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        val btnCloseSheet: ImageButton = findViewById(R.id.btnCloseSheet)
        btnCloseSheet.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        // --- Configuración de TODOS los Items de Información ---

        // Sección: Daños y Muestreo
        setupInfoItem(
            itemId = R.id.itemDamage,
            iconRes = R.drawable.ic_damage,
            title = "Daños a los cultivos",
            onClickAction = {
                showBottomSheetWithContent(
                    "Daños a los Cultivos",
                    "El gusano cogollero causa perforaciones en las hojas, dejando hileras de hoyos. En ataques severos, destruye el cogollo de la planta, impidiendo su crecimiento y la formación de la mazorca.",
                    R.drawable.img_cogollero1
                )
            }
        )
        setupInfoItem(
            itemId = R.id.itemSampling,
            iconRes = R.drawable.ic_sampling,
            title = "Cómo realizar el muestreo",
            onClickAction = {
                showBottomSheetWithContent(
                    "Cómo Realizar el Muestreo",
                    "Para el muestreo, se recomienda revisar 5 puntos al azar en el campo en un patrón de 'X' o 'W'. En cada punto, inspecciona 20 plantas consecutivas y registra cuántas tienen daño fresco para determinar el nivel de infestación.",
                    R.drawable.img_cogollero2
                )
            }
        )

        // Sección: Tipos de Control
        setupInfoItem(
            itemId = R.id.itemControlBiological,
            iconRes = R.drawable.ic_biological,
            title = "Control Biológico",
            onClickAction = {
                showBottomSheetWithContent(
                    "Control Biológico",
                    "Consiste en el uso de enemigos naturales de la plaga. Se pueden liberar avispas parasitoides como Trichogramma spp. o aplicar bioinsecticidas a base de bacterias como Bacillus thuringiensis, que son efectivas contra las larvas.",
                    R.drawable.img_cogollero3
                )
            }
        )
        setupInfoItem(
            itemId = R.id.itemControlCultural,
            iconRes = R.drawable.ic_cultural,
            title = "Control Cultural",
            onClickAction = {
                showBottomSheetWithContent(
                    "Control Cultural",
                    "Son prácticas agrícolas que reducen la incidencia de la plaga. Incluyen la rotación de cultivos, la destrucción de residuos de cosecha (rastrojo) donde la plaga sobrevive y mantener una fertilización balanceada para tener plantas más fuertes.",
                    R.drawable.img_cogollero1
                )
            }
        )
        setupInfoItem(
            itemId = R.id.itemControlEthological,
            iconRes = R.drawable.ic_ethological,
            title = "Control Etológico",
            onClickAction = {
                showBottomSheetWithContent(
                    "Control Etológico",
                    "Se basa en el comportamiento de la plaga. El uso de trampas con feromonas sexuales es muy común para monitorear la llegada de los machos adultos a la parcela y decidir el momento oportuno para otras medidas de control.",
                    R.drawable.img_cogollero2
                )
            }
        )
        setupInfoItem(
            itemId = R.id.itemControlChemical,
            iconRes = R.drawable.ic_chemical,
            title = "Control Químico",
            onClickAction = {
                showBottomSheetWithContent(
                    "Control Químico",
                    "Es el uso de insecticidas sintéticos. Es crucial rotar los productos con diferentes modos de acción para evitar la resistencia. Aplica solo cuando la plaga alcance el umbral de daño económico y siempre sigue las recomendaciones de un profesional.",
                    R.drawable.img_cogollero3
                )
            }
        )

        // Sección: Manejo de Resistencia
        setupInfoItem(
            itemId = R.id.itemResistance,
            iconRes = R.drawable.ic_resistance,
            title = "Manejo de Resistencia",
            onClickAction = {
                showBottomSheetWithContent(
                    "Manejo de Resistencia",
                    "La resistencia ocurre cuando las plagas ya no mueren con insecticidas que antes eran efectivos. Para evitarla, es fundamental rotar los grupos químicos, usar las dosis recomendadas y combinar diferentes métodos de control (Manejo Integrado de Plagas).",
                    R.drawable.img_cogollero1
                )
            }
        )
    }

    private fun showBottomSheetWithContent(title: String, content: String, imageRes: Int) {
        // 1. Actualiza el contenido
        tvSheetTitle.text = title
        tvSheetContent.text = content
        ivSheetImage.setImageResource(imageRes)

        // 2. Muestra el sheet
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupInfoItem(itemId: Int, iconRes: Int, title: String, subtitle: String? = null, onClickAction: () -> Unit) {
        val itemLayout: LinearLayout = findViewById(itemId)
        val ivIcon: ImageView = itemLayout.findViewById(R.id.ivIcon)
        val tvTitle: TextView = itemLayout.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = itemLayout.findViewById(R.id.tvSubtitle)

        ivIcon.setImageResource(iconRes)
        tvTitle.text = title

        if (subtitle != null) {
            tvSubtitle.text = subtitle
            tvSubtitle.visibility = View.VISIBLE
        } else {
            tvSubtitle.visibility = View.GONE
        }

        itemLayout.setOnClickListener {
            onClickAction()
        }
    }
}