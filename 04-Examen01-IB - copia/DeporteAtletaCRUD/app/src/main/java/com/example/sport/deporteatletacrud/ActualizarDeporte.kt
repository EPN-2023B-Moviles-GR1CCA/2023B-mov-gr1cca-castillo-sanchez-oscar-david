package com.example.sport.deporteatletacrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.example.sport.deporteatletacrud.db.DbDeporte

class ActualizarDeporte : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_deporte)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val idDeporte = Deporte.idDeporteSelected
        var deporte = DbDeporte(null, "", "", 0.0, false, this)
        deporte = deporte.getDeporteById(idDeporte)

        var id = findViewById<EditText>(R.id.update_deporte)
        id.setText(deporte.getidDeporte().toString())
        var nombre = findViewById<EditText>(R.id.update_nombDeporte)
        nombre.setText(deporte.getnombreDeporte())
        var fundacion = findViewById<EditText>(R.id.update_fundacionD)
        fundacion.setText(deporte.getfechaFundacion())
        var popularidad = findViewById<EditText>(R.id.update_popularidadD)
        popularidad.setText(deporte.getpopularidadGlobal().toString())
        var nivelCompetitivo  = findViewById<Switch>(R.id.update_switchProfD)
        nivelCompetitivo.isChecked=deporte.getnivelCompetitivo().toString().toBoolean()

        val btn_actualizarDeporte = findViewById<Button>(R.id.btn_upd_deporte)
        btn_actualizarDeporte.setOnClickListener {
            // Deporte actualizado
            deporte.setnombreDeporte(nombre.text.toString())
            deporte.setfechaFundacion(fundacion.text.toString())
            deporte.setpopularidadGlobal(popularidad.text.toString().toDouble())
            deporte.setnivelCompetitivo(nivelCompetitivo.isChecked)
            val resultado = deporte.updateDeporte()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
            irActividad(Deporte::class.java)
            finish()
        }
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
        val id = findViewById<EditText>(R.id.update_deporte)
        id.setText("")
        val nombre = findViewById<EditText>(R.id.update_nombDeporte)
        nombre.setText("")
        val fundacion = findViewById<EditText>(R.id.update_fundacionD)
        fundacion.setText("")
        val popularidad = findViewById<EditText>(R.id.update_popularidadD)
        popularidad.setText("")
        val nivelConpetitivo = findViewById<Switch>(R.id.update_switchProfD)
        nivelConpetitivo.isChecked=false
        id.requestFocus()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}