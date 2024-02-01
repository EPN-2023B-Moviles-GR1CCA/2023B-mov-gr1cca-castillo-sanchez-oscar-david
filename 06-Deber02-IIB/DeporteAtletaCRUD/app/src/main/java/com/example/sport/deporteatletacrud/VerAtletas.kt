package com.example.sport.deporteatletacrud

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sport.deporteatletacrud.db.DbAtleta
import com.example.sport.deporteatletacrud.db.DbDeporte


class VerAtletas : AppCompatActivity() {

    companion object {
        var idAtletaSelected = 0
    }
        @SuppressLint("MissingFlattedID","MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_ver_atletas)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val idDeporte = Deporte.idDeporteSelected
            var deporteAux = DbDeporte(null, "", "", 0.0, false, this)

            val textViewAtleta = findViewById<TextView>(R.id.ver_deportePadre)
            textViewAtleta.text = deporteAux.getDeporteById(idDeporte).getnombreDeporte()

            val btnCrearAtleta = findViewById<Button>(R.id.btn_crearAtleta)
            btnCrearAtleta.setOnClickListener {
                irActividad(Atleta::class.java)
            }
            showListView(idDeporte)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Termina la actividad y vuelve a la actividad anterior
                irActividad(Deporte::class.java)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

        fun showListView(id: Int) {
            // ListView Libros
            val objetoAtleta = DbAtleta(null, "", 0, 0, 0.0, this)
            val listViewAtleta= findViewById<ListView>(R.id.listView_atleta)
            val adaptador = AtletaAdapter(this,objetoAtleta.showAtletas(id) )
            listViewAtleta.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listViewAtleta)
        }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_atleta, menu)

        // Obtener la información del elemento seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo

        // Obtener el objeto DbAtleta seleccionado
        val listViewAtleta = findViewById<ListView>(R.id.listView_atleta)
        val atletaSeleccionado = listViewAtleta.adapter.getItem(info.position) as DbAtleta
        // Obtener el ID del atleta seleccionado
        idAtletaSelected = Integer.parseInt(atletaSeleccionado.getidAtleta().toString())-1
    }

        override fun onContextItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_edit_atleta -> {
                    irActividad(ActualizarAtleta::class.java)
                    return true
                }
                R.id.action_delete_atleta -> {
                    abrirDialogo()
                    return true
                }
                else -> super.onContextItemSelected(item)
            }
        }

        fun abrirDialogo() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Desea eliminar este Atleta?")
            builder.setPositiveButton(
                "SI",
                DialogInterface.OnClickListener { dialog, which ->
                    val atleta = DbAtleta(null, "", 0, 0, 0.0, this)
                    val resultado = atleta.deleteAtleta(idAtletaSelected)
                    if (resultado > 0) {
                        Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                    }
                    val idDeporte = Deporte.idDeporteSelected
                    showListView(idDeporte)
                }
            )
            builder.setNegativeButton(
                "NO",
                null
            )

            val dialogo = builder.create()
            dialogo.show()
        }

        fun irActividad(
            clase: Class<*>
        ) {
            val intent = Intent(this, clase)
            startActivity(intent)
        }

    }
