package com.example.sport.deporteatletacrud

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.sport.deporteatletacrud.db.DbAtleta
import android.widget.Button
import android.widget.TextView
import com.example.sport.deporteatletacrud.db.DbDeporte

class ActualizarAtleta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_atleta)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val idAtleta = VerAtletas.idAtletaSelected

        var atleta = DbAtleta(null, "", 0, 0, 0.0,  this)
        atleta = atleta.getAtletaById(idAtleta)

        var id = findViewById<EditText>(R.id.update_id_atleta)
        id.setText(atleta.getidAtleta().toString())
        var nombre = findViewById<EditText>(R.id.update_nombAtleta)
        nombre.setText(atleta.getnombreAtleta())
        var edad = findViewById<EditText>(R.id.update_edad)
        edad.setText(atleta.getedadAtleta().toString())
        var deporte = findViewById<EditText>(R.id.update_deporte)
        deporte.setText(atleta.getdeporte().toString())
        var altura = findViewById<EditText>(R.id.update_altura)
        altura.setText(atleta.getaltura().toString())


        val btn_actualizar_atleta = findViewById<Button>(R.id.btn_upd_atleta)
        btn_actualizar_atleta.setOnClickListener {
            atleta.setnombreAtleta(nombre.text.toString())
            atleta.setedadAtleta(edad.text.toString().toInt())
            atleta.setdeporte(deporte.text.toString().toInt())
            atleta.setaltura(altura.text.toString().toDouble())
            val resultado = atleta.updateAtleta()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
            irActividad(VerAtletas::class.java)
            finish()
        }
        val idDeporte = Deporte.idDeporteSelected
        var deporteAux = DbDeporte(null, "", "", 0.0, false, this)

        val textViewAtleta = findViewById<TextView>(R.id.tv_titleDeporte2)
        textViewAtleta.text = deporteAux.getDeporteById(idDeporte).getnombreDeporte()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Termina la actividad y vuelve a la actividad anterior
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun cleanEditText() {
        var id = findViewById<EditText>(R.id.update_id_atleta)
        id.setText("")
        var nombre = findViewById<EditText>(R.id.update_nombAtleta)
        nombre.setText("")
        var edad = findViewById<EditText>(R.id.update_edad)
        edad.setText("")
        var deporte = findViewById<EditText>(R.id.update_deporte)
        deporte.setText("")
        var altura = findViewById<EditText>(R.id.update_altura)
        altura.setText("")
        id.requestFocus()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}