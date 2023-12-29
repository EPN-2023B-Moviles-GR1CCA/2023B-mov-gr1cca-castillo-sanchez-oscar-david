package com.example.gr1accdlrc2023b


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar


class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arreglosBEntrenador
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val  botonAnadirListView = findViewById<Button>(
            R.id.btn_anadir_list_view
        )
        botonAnadirListView
            .setOnClickListener {
                anadirEntrenador(adaptador)
            }
    }
    var posicionItemSeleccionado = 0

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    fun anadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(BEntrenador(1, "Luis","l@l.com"))
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSnackBar(texto: String){
        com.google.android.material.snackbar.Snackbar.make(
            findViewById(R.id.lv_list_view),
            texto,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar -> {
                mostrarSnackBar("${posicionItemSeleccionado}")
                return true
            }

            R.id.mi_eliminar -> {
                mostrarSnackBar("${posicionItemSeleccionado}")
                abrirDialogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{dialog, which -> mostrarSnackBar("Eliminar aceptado")}

        )
        builder.setNegativeButton(
            "Cancelar",
            null

        )
        val opciones = resources.getStringArray(
            R.array.string_array_opciones_dialogo
        )
        val seleccionPrevia = booleanArrayOf(
            true,
            false,
            false
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {dialog,
            which,
            isChecked ->
                mostrarSnackBar("Dio click en el item ${which}")
            }
        )
        val dialogo = builder.create()
        dialogo.show()
    }

}