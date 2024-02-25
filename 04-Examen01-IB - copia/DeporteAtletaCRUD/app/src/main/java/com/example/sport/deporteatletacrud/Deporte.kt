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
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sport.deporteatletacrud.db.DbAtleta
import com.example.sport.deporteatletacrud.db.DbDeporte


class Deporte: AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    companion object{
        var idDeporteSelected = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deporte)
        showListViewDeporte()
        val nombre=findViewById<EditText>(R.id.editTextNombreD)
        val creation=findViewById<EditText>(R.id.editTextCreationDateD)
        val popul=findViewById<EditText>(R.id.editTextPopulD)
        val prof=findViewById<Switch>(R.id.switchProfD)
        val btnCrear= findViewById<Button>(R.id.btnCrear)

        creation.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                // Formatea la fecha y colócala en el EditText
                val selectedDate = "${dayOfMonth}/${monthOfYear + 1}/$year"
                creation.setText(selectedDate)
            }, year, month, day)

            dpd.show()
        }
        btnCrear.setOnClickListener {
            val deporte= DbDeporte(
                null,
                nombre.text.toString(),
                creation.text.toString(),
                popul.text.toString().toDouble(),
                prof.isChecked,
                this

            )

            val result = deporte.insertDeporte()
            if (result>0){
                Toast.makeText(this, "REGISTRO GUARDADO CORRECTAMENTE", Toast.LENGTH_LONG).show()
                cleanEditText()
                showListViewDeporte()
            }else{
                Toast.makeText(this,"ERROR AL GUARDAR EL REGISTRO", Toast.LENGTH_LONG).show()
            }
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Presiona nuevamente para salir", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
    fun cleanEditText(){
        val nombre=findViewById<EditText>(R.id.editTextNombreD)
        nombre.setText("")
        val creation=findViewById<EditText>(R.id.editTextCreationDateD)
        creation.setText("")
        val popul=findViewById<EditText>(R.id.editTextPopulD)
        popul.setText("")
        val prof=findViewById<Switch>(R.id.switchProfD)
        prof.isChecked=false
        nombre.requestFocus()
    }



    fun showListViewDeporte() {
        val deporte = DbDeporte(null,"","",0.0,false,this)
        val listViewDeporte= findViewById<ListView>(R.id.listView_Deporte)
        val adaptador = DeporteAdapter(this,deporte.showDeportes() )
        listViewDeporte.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewDeporte)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_deporte, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val listViewDeporte = findViewById<ListView>(R.id.listView_Deporte)
        val deporteSeleccionado = listViewDeporte.adapter.getItem(info.position) as DbDeporte
        // Obtener el ID del atleta seleccionado
        idDeporteSelected = Integer.parseInt(deporteSeleccionado.getidDeporte().toString())-1
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_deporte -> {
                irActividad(ActualizarDeporte::class.java)
                return true
            }
            R.id.action_delete_deporte -> {
                abrirDialogo()
                return true
            }
            R.id.action_ver_atletas -> {
                irActividad(VerAtletas::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar esta Deporte?")
        builder.setPositiveButton(
            "SI",
            DialogInterface.OnClickListener { dialog, which ->
                val deporte = DbDeporte(null, "", "", 0.0, true, this)
                val resultado = deporte.deleteDeporte(idDeporteSelected)
                if (resultado > 0) {
                    Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                    showListViewDeporte()
                } else {
                    Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                }
            }
        )
        builder.setNegativeButton(
            "NO",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }


}
