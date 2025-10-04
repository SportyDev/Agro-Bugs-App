package com.sportydev.agrobugs

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SearchImageActivity : BaseActivity() {

    // Variables para CameraX
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    // Launcher para solicitar el permiso de la cámara
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(this, "Permiso de cámara denegado.", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad si no se da el permiso
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_image)

        // Solicitar permiso de la cámara
        checkCameraPermissionAndStart()

        // Configurar los botones de la interfaz
        setupActionButtons()

        // Inicializar el ejecutor para tareas de cámara en segundo plano
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setupActionButtons() {
        findViewById<ImageButton>(R.id.btnClose).setOnClickListener {
            finish() // Cierra la cámara
        }

        findViewById<android.view.View>(R.id.btnShutter).setOnClickListener {
            takePhoto() // Captura la foto
        }

        // agregar la lógica para los demás botones (flash, galería)
    }

    private fun checkCameraPermissionAndStart() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permiso ya concedido, iniciar cámara
                startCamera()
            }
            else -> {
                // Pedir el permiso
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Se usa para vincular el ciclo de vida de la cámara al de la actividad
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Configura la vista previa
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(findViewById<PreviewView>(R.id.cameraPreview).surfaceProvider)
                }

            // Configura la captura de imagen
            imageCapture = ImageCapture.Builder().build()

            // Selecciona la cámara trasera por defecto
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Desvincula todo antes de volver a vincular
                cameraProvider.unbindAll()

                // Vincula los casos de uso (preview y captura) a la cámara
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e("CameraX", "Fallo al vincular los casos de uso", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Obtiene una referencia estable del caso de uso de captura de imágenes
        val imageCapture = imageCapture ?: return

        // Crea un nombre para el archivo con la fecha y hora
        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/AgroBugs")
            }
        }

        // Crea el objeto de opciones de salida
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Configura el listener para después de que la foto ha sido tomada
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraX", "Error al guardar la foto: ${exc.message}", exc)
                    Toast.makeText(baseContext, "Error al guardar la foto.", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "¡Foto guardada con éxito en la galería!"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d("CameraX", "Foto guardada: ${output.savedUri}")
                    // val intent = Intent(this@SearchImageActivity, ResultActivity::class.java)
                    // intent.putExtra("image_uri", output.savedUri.toString())
                    // startActivity(intent)
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}