package com.sportydev.agrobugs

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException

class AdminBd(private val contexto: Context) : SQLiteOpenHelper(contexto, DATABASE_NAME, null, DATABASE_VERSION) {

    private val dbPath: String = contexto.getDatabasePath(DATABASE_NAME).path

    companion object {
        //nombre del documento de la base de datos
        private const val DATABASE_NAME = "BDBichos.db"
        private const val DATABASE_VERSION = 1
    }

    init {
        // Este bloque se asegura que la base de datos exista
        createDatabase()
    }

    /**
     * Comprueba si la base de datos ya existe; si no, la copia desde 'assets'.
     */
    fun createDatabase() {
        if (!databaseExists()) {
            this.readableDatabase
            this.close()
            try {
                copyDatabase()
            } catch (e: IOException) {
                throw Error("Error copiando la base de datos desde assets")
            }
        }
    }

    /**
     * Revisa si el archivo de la base de datos ya está en el directorio de la app.
     */
    private fun databaseExists(): Boolean {
        return contexto.getDatabasePath(DATABASE_NAME).exists()
    }

    /**
     * Copia la base de datos desde la carpeta 'assets' al directorio de la app.
     */
    private fun copyDatabase() {
        val inputStream = contexto.assets.open(DATABASE_NAME)
        val outputStream = FileOutputStream(dbPath)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}