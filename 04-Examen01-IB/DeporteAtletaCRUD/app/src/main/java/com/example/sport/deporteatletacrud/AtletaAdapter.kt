package com.example.sport.deporteatletacrud

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sport.deporteatletacrud.db.DbAtleta

class AtletaAdapter(context: Context, atletas: ArrayList<DbAtleta>) : ArrayAdapter<DbAtleta>(context, 0, atletas) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Infla el layout personalizado si es necesario
        val rowView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_atleta, parent, false)
        // Obtiene el objeto AtletaModel para la posición actual
        val atleta = getItem(position)

        // Encuentra los TextViews en el layout
        val tvIdAtleta = rowView.findViewById<TextView>(R.id.tvIdAtleta)
        val tvNombreAtleta = rowView.findViewById<TextView>(R.id.tvNombreAtleta)
        val tvEdadAtleta = rowView.findViewById<TextView>(R.id.tvEdadAtleta)
        val tvDeporteAtleta = rowView.findViewById<TextView>(R.id.tvDeporteAtleta)
        val tvAlturaAtleta = rowView.findViewById<TextView>(R.id.tvAlturaAtleta)

        // Asigna los datos del atleta a los TextViews
        tvIdAtleta.text= atleta?.getidAtleta().toString()
        tvNombreAtleta.text = atleta?.getnombreAtleta()
        tvEdadAtleta.text = atleta?.getedadAtleta().toString()
        tvDeporteAtleta.text = atleta?.getdeporte().toString()
        tvAlturaAtleta.text = atleta?.getaltura().toString()

        return rowView
    }
}


