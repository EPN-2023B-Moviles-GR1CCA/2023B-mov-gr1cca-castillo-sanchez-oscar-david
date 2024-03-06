package com.example.sport.deporteatletacrud.db

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.sport.deporteatletacrud.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.time.format.DateTimeFormatter

class DbDeporte(
    private var idDeporte: String?,
    private var nombreDeporte: String,
    private var fechaFundacion: String,
    private var popularidadGlobal: Double,
    private var nivelCompetitivo: Boolean,
    private val context: Context
) {


    init {
        idDeporte
        nombreDeporte
        fechaFundacion
        popularidadGlobal
        nivelCompetitivo
    }

    fun setidDeporte(idDeporte: String){
        this.idDeporte= idDeporte
    }

    fun setnombreDeporte(nombreDeporte: String){
        this.nombreDeporte=nombreDeporte
    }
    fun setfechaFundacion(fechaFundacion: String){
        this.fechaFundacion= fechaFundacion
    }
    fun setpopularidadGlobal(popularidadGlobal: Double){
        this.popularidadGlobal=popularidadGlobal
    }
    fun setnivelCompetitivo(nivelCompetitivo: Boolean){
        this.nivelCompetitivo=nivelCompetitivo
    }

    fun getidDeporte(): String? {
        return this.idDeporte
    }

    fun getnombreDeporte(): String? {
        return this.nombreDeporte
    }

    fun getfechaFundacion(): String? {
        return this.fechaFundacion
    }

    fun getpopularidadGlobal(): Double? {
        return this.popularidadGlobal
    }

    fun getnivelCompetitivo(): Boolean? {
        return this.nivelCompetitivo
    }


    fun insertDeporte() {
        val db = FirebaseFirestore.getInstance()
        val deporteMap = hashMapOf(
            "nombre_deporte" to nombreDeporte,
            "nivel_competitivo" to nivelCompetitivo,
            "fecha_fundacion" to fechaFundacion,
            "popularidad_global" to popularidadGlobal
        )

        // Agregar el nuevo deporte a la colección y Firestore generará un ID único automáticamente
        db.collection("deportes").add(deporteMap).addOnSuccessListener { documentReference ->
            Log.d(TAG, "Deporte agregado con ID: ${documentReference.id}")
            // Si necesitas realizar alguna acción con el ID del documento recién creado, puedes hacerlo aquí
            // Por ejemplo, actualizar la UI o almacenar el ID para uso futuro
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error agregando deporte", e)
        }
    }




    fun showDeportes(): Task<QuerySnapshot> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("deportes").get()
    }



    fun deleteDeporte(docId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("deportes").document(docId).delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    override fun toString(): String {
        val texto=
                    "Deporte: ${nombreDeporte}\n"+
                    "Fundación: ${fechaFundacion}\n"+
                    "Popularidad: ${popularidadGlobal}\n"+
                    "Es Profesional?: ${nivelCompetitivo}\n"
        return texto
    }
}