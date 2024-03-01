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

class Atleta : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atleta)

        db = FirebaseFirestore.getInstance()

        val nombre = findViewById<EditText>(R.id.txt_nombre_atleta)
        val edad = findViewById<EditText>(R.id.txt_edad)
        val altura = findViewById<EditText>(R.id.txt_altura)
        val deporteId = intent.getStringExtra("idDeporte") // Suponiendo que recibes el ID del deporte como extra
        val deporteTextView = findViewById<TextView>(R.id.txt_deporte)
        deporteTextView.text = deporteId
        if (deporteId != null) {
            db.collection("deportes").document(deporteId).get().addOnSuccessListener { documentoDeporte ->
                if (documentoDeporte.exists()) {
                    val nombreDeporte = documentoDeporte.getString("nombre_deporte") ?: ""
                    val textViewDeporte = findViewById<TextView>(R.id.tv_titleDeporte)
                    textViewDeporte.text = nombreDeporte
                } else {
                    Toast.makeText(this, "Deporte no encontrado", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar el deporte: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        val btnInsertar = findViewById<Button>(R.id.btn_insert_atleta)
        btnInsertar.setOnClickListener {
            insertarAtleta(nombre.text.toString(), edad.text.toString().toInt(), altura.text.toString().toDouble(), deporteId ?: "")
            val intent = Intent(this, VerAtletas::class.java).apply {
                putExtra("deporteId", Deporte.idDeporteSelected)
            }

            startActivity(intent)
        }
    }

    private fun insertarAtleta(nombre: String, edad: Int, altura: Double, deporteId: String) {
        val atletaMap = hashMapOf(
            "nombre_atleta" to nombre,
            "edad_atleta" to edad,
            "deporte" to deporteId,
            "altura" to altura
        )

        db.collection("atletas").add(atletaMap).addOnSuccessListener {
            Toast.makeText(this, "Registro guardado", Toast.LENGTH_LONG).show()
            cleanEditText()
            // Opcional: Redirigir a la actividad donde visualizas los atletas
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error al insertar registro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanEditText() {
        findViewById<EditText>(R.id.txt_nombre_atleta).text.clear()
        findViewById<EditText>(R.id.txt_edad).text.clear()
        findViewById<EditText>(R.id.txt_deporte).text.clear()
        findViewById<EditText>(R.id.txt_altura).text.clear()
        findViewById<EditText>(R.id.txt_nombre_atleta).requestFocus()
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
