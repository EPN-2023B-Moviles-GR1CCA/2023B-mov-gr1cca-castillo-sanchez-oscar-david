package com.example.sport.deporteatletacrud

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sport.deporteatletacrud.db.DbDeporte
import com.google.firebase.firestore.FirebaseFirestore

class Deporte : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var db: FirebaseFirestore

    companion object {
        var idDeporteSelected: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deporte)
        db = FirebaseFirestore.getInstance()

        configureDateInput()
        configureCreateButton()

        showListViewDeporte()
    }

    private fun configureDateInput() {
        val creation = findViewById<EditText>(R.id.editTextCreationDateD)
        creation.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Formato de fecha: DD/MM/YYYY
                val selectedDate = "${dayOfMonth}/${monthOfYear + 1}/$year"
                creation.setText(selectedDate)
            }, year, month, day)
            dpd.show()
        }
    }

    private fun configureCreateButton() {
        val btnCrear = findViewById<Button>(R.id.btnCrear)
        btnCrear.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.editTextNombreD).text.toString()
            val creation = findViewById<EditText>(R.id.editTextCreationDateD).text.toString()
            val popul = findViewById<EditText>(R.id.editTextPopulD).text.toString().toDouble()
            val prof = findViewById<Switch>(R.id.switchProfD).isChecked

            val deporte = DbDeporte(null, nombre, creation, popul, prof, this)
            deporte.insertDeporte()
            Toast.makeText(this, "Deporte agregado", Toast.LENGTH_SHORT).show()
            cleanEditText()
            showListViewDeporte()

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun showListViewDeporte() {
        val listViewDeporte = findViewById<ListView>(R.id.listView_Deporte)
        val deporte = DbDeporte(null, "", "", 0.0, false, this)
        deporte.showDeportes().addOnSuccessListener { documents ->
            val deportesList = ArrayList<DbDeporte>()
            for (document in documents) {
                val deporteItem = DbDeporte(
                    document.id,
                    document.getString("nombre_deporte") ?: "",
                    document.getString("fecha_fundacion") ?: "",
                    document.getDouble("popularidad_global") ?: 0.0,
                    document.getBoolean("nivel_competitivo") ?: false,
                    this
                )
                deportesList.add(deporteItem)
            }
            val adapter = DeporteAdapter(this, deportesList)
            listViewDeporte.adapter = adapter
            registerForContextMenu(listViewDeporte)
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error al cargar deportes: ${exception.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_deporte, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        // Suponiendo que tu adaptador provee los objetos DbDeporte con el ID de Firestore incluido
        val deporteSeleccionado = (v as ListView).adapter.getItem(info.position) as DbDeporte
        idDeporteSelected =
            deporteSeleccionado.getidDeporte().toString() // Asume que 'id' es el ID de Firestore
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit_deporte -> {
                val intent = Intent(this, ActualizarDeporte::class.java).apply {
                    putExtra("deporteId", idDeporteSelected)
                }
                startActivity(intent)
                return true
            }
            R.id.action_delete_deporte -> {
                mostrarDialogoConfirmacion()
                return true
            }
            R.id.action_ver_atletas -> {

                val intent = Intent(this, VerAtletas::class.java).apply {
                    putExtra("deporteId", idDeporteSelected)
                }

                startActivity(intent)

                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmación")
            setMessage("¿Desea eliminar este deporte?")
            setPositiveButton("Sí") { dialog, which ->
                val deporte = DbDeporte(null, "", "", 0.0, true, this@Deporte)
                deporte.deleteDeporte(idDeporteSelected)
                showListViewDeporte()
            }
            setNegativeButton("No", null)
            show()
        }
    }

    private fun cleanEditText() {
        findViewById<EditText>(R.id.editTextNombreD).setText("")
        findViewById<EditText>(R.id.editTextCreationDateD).setText("")
        findViewById<EditText>(R.id.editTextPopulD).setText("")
        findViewById<Switch>(R.id.switchProfD).isChecked = false
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
