package com.example.sport.deporteatletacrud

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sport.deporteatletacrud.db.DbAtleta
import com.google.firebase.firestore.FirebaseFirestore

class ActualizarAtleta : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_atleta)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = FirebaseFirestore.getInstance()

        // Suponiendo que pasas el ID del atleta como un extra en el intent
        val idAtleta = intent.getStringExtra("idAtleta") ?: ""
        val idDeporte = intent.getStringExtra("idDeporte") ?: ""
        cargarDatosAtleta(idAtleta)

        val btnActualizarAtleta = findViewById<Button>(R.id.btn_upd_atleta)
        btnActualizarAtleta.setOnClickListener {
            actualizarAtleta(idAtleta)
            val intent = Intent(this, VerAtletas::class.java)
            intent.putExtra("deporteId",idDeporte)
            startActivity(intent)
            finish()
        }
    }

    private fun cargarDatosAtleta(idAtleta: String) {
        db.collection("atletas").document(idAtleta).get().addOnSuccessListener { document ->
            if (document != null) {
                val nombre = document.getString("nombre_atleta") ?: ""
                val edad = document.getLong("edad_atleta")?.toInt() ?: 0
                val idDeporte = document.getString("deporte") ?: "" // Este es el ID del deporte, no el nombre
                val altura = document.getDouble("altura") ?: 0.0

                // Actualiza los campos con los datos del atleta
                findViewById<EditText>(R.id.update_nombAtleta).setText(nombre)
                findViewById<EditText>(R.id.update_edad).setText(edad.toString())
                findViewById<EditText>(R.id.update_altura).setText(altura.toString())

                // Ahora, realiza otra consulta para obtener el nombre del deporte usando el ID de deporte
                if (idDeporte.isNotEmpty()) {
                    db.collection("deportes").document(idDeporte).get().addOnSuccessListener { documentoDeporte ->
                        if (documentoDeporte.exists()) {
                            val nombreDeporte = documentoDeporte.getString("nombre_deporte") ?: ""
                            val textViewDeporte = findViewById<TextView>(R.id.tv_titleDeporte2)
                            textViewDeporte.text = nombreDeporte
                        } else {
                            Toast.makeText(this, "Deporte no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Error al cargar el deporte: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Atleta no encontrado", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error al cargar atleta: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun actualizarAtleta(idAtleta: String) {
        val nombre = findViewById<EditText>(R.id.update_nombAtleta).text.toString()
        val edad = findViewById<EditText>(R.id.update_edad).text.toString().toInt()
        val deporte = findViewById<EditText>(R.id.update_deporte).text.toString()
        val altura = findViewById<EditText>(R.id.update_altura).text.toString().toDouble()

        val atletaMap = hashMapOf(
            "nombre_atleta" to nombre,
            "edad_atleta" to edad,
            "deporte" to deporte,
            "altura" to altura
        )

        db.collection("atletas").document(idAtleta).set(atletaMap).addOnSuccessListener {
            Toast.makeText(this, "Atleta actualizado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error al actualizar atleta: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
