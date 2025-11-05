package com.sportydev.agrobugs

import android.annotation.SuppressLint
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
    @SuppressLint("Range")
    fun getPestById(id: Int): Plaga? {
        var plaga: Plaga? = null
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Plaga WHERE idPlaga = ?", arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            // Obtenemos el texto con los nombres de las imágenes y lo convertimos a lista
            val imagesString = cursor.getString(cursor.getColumnIndex("imageName"))
            // Mapeamos las columnas de la BD a las propiedades del data class

            plaga = Plaga(
                idPlaga = cursor.getInt(cursor.getColumnIndex("idPlaga")),
                nomPlaga = cursor.getString(cursor.getColumnIndex("nomPlaga")),
                nomcientifico = cursor.getString(cursor.getColumnIndex("nomCientifico")),
                descPlaga = cursor.getString(cursor.getColumnIndex("descPlaga")),
                imageName = imagesString.split(','),

                damage = cursor.getString(cursor.getColumnIndex("damage")),
                muestreo = cursor.getString(cursor.getColumnIndex("muestreo")),
                biolControl = cursor.getString(cursor.getColumnIndex("biolControl")),
                cultControl = cursor.getString(cursor.getColumnIndex("cultControl")),
                etolControl = cursor.getString(cursor.getColumnIndex("etolControl")),
                quimControl = cursor.getString(cursor.getColumnIndex("quimControl")),
                resistManejo = cursor.getString(cursor.getColumnIndex("resistManejo"))
            )
        }

        cursor.close()
        return plaga
    }

    @SuppressLint("Range")
    fun getAllPests(): List<Plaga> {
        val pestList = mutableListOf<Plaga>()
        val db = this.readableDatabase
        // Asegúrate de que el nombre de la tabla "pests" y los nombres de las columnas son correctos
        val cursor = db.rawQuery("SELECT * FROM Plaga", null)

        if (cursor.moveToFirst()) {
            do {
                val imagesString = cursor.getString(cursor.getColumnIndex("imageName"))

                val Plaga = Plaga(
                    idPlaga = cursor.getInt(cursor.getColumnIndex("idPlaga")),
                    nomPlaga = cursor.getString(cursor.getColumnIndex("nomPlaga")),
                    nomcientifico = cursor.getString(cursor.getColumnIndex("nomCientifico")),
                    descPlaga = cursor.getString(cursor.getColumnIndex("descPlaga")),
                    imageName = imagesString.split(',')
                )
                pestList.add(Plaga)
            } while (cursor.moveToNext())
        }

        cursor.close()
//        db.close()
        return pestList
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}