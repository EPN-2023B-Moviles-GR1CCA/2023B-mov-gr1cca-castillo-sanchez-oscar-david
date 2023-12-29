package com.example.gr1accdlrc2023b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringArrayExtra("nombre")
        val apellido = intent.getBooleanArrayExtra("apellido")
        val edad= intent.getIntExtra("edad", 0)
        mostrarSnackbar("${nombre} ${apellido} ${edad}")
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton
            .setOnClickListener {devolverRespuesta()}
    }

    fun devolverRespuesta(){
        val intentoDevolverParametros = Intent()
        intentoDevolverParametros.putExtra("nombreModificado", "OC")
        intentoDevolverParametros.putExtra("edadModificada", 33)
        setResult(
            RESULT_OK,
            intentoDevolverParametros
        )
        finish()
    }

    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.id_layout_intents),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }
}