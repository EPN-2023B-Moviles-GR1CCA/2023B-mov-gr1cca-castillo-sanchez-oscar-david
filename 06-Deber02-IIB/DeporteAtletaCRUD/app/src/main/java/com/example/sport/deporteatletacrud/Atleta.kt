package com.example.sport.deporteatletacrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sport.deporteatletacrud.db.DbAtleta
import com.example.sport.deporteatletacrud.db.DbDeporte

class Atleta : AppCompatActivity() {

    var idItemSeleccionado = 0

    @SuppressLint("MissingFlattedID","MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atleta)

        val nombre = findViewById<EditText>(R.id.txt_nombre_atleta)
        nombre.requestFocus()
        val edad = findViewById<EditText>(R.id.txt_edad)
        val altura = findViewById<EditText>(R.id.txt_altura)
        val deporte = findViewById<EditText>(R.id.txt_deporte)

        val btnInsertar = findViewById<Button>(R.id.btn_insert_atleta)
        btnInsertar.setOnClickListener {
            val atleta= DbAtleta(
                null,
                nombre.text.toString(),
                edad.text.toString().toInt(),
                deporte.text.toString().toInt(),
                altura.text.toString().toDouble(),
                this
            )
            val resultado = atleta.insertAtleta()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL INSERTAR REGISTRO", Toast.LENGTH_LONG).show()
            }
            irActividad(VerAtletas::class.java)
        }
        val idDep=Deporte.idDeporteSelected+1
        deporte.setText(idDep.toString())

        val idDeporte = Deporte.idDeporteSelected
        var deporteAux = DbDeporte(null, "", "", 0.0, false, this)

        val textViewAtleta = findViewById<TextView>(R.id.tv_titleDeporte)
        textViewAtleta.text = deporteAux.getDeporteById(idDeporte).getnombreDeporte()
    }

    fun cleanEditText() {
        val nombre = findViewById<EditText>(R.id.txt_nombre_atleta)
        nombre.setText("")
        val edad = findViewById<EditText>(R.id.txt_edad)
        edad.setText("")
        val deporte = findViewById<EditText>(R.id.txt_deporte)
        deporte.setText("")
        val altura = findViewById<EditText>(R.id.txt_altura)
        altura.setText("")
        nombre.requestFocus()
    }


    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}