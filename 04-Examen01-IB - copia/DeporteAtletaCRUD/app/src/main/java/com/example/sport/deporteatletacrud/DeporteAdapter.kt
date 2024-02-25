package com.example.sport.deporteatletacrud

import com.example.sport.deporteatletacrud.db.DbDeporte
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sport.deporteatletacrud.db.DbAtleta

class DeporteAdapter(context: Context, atletas: ArrayList<DbDeporte>) : ArrayAdapter<DbDeporte>(context, 0, atletas) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Infla el layout personalizado si es necesario
        val rowView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_deporte, parent, false)
        // Obtiene el objeto AtletaModel para la posición actual
        val deporte = getItem(position)

        // Encuentra los TextViews en el layout
        val idDeporte = rowView.findViewById<TextView>(R.id.tvIdDeporte)
        val tvNombreDeporte = rowView.findViewById<TextView>(R.id.tvNombreDeporte)
        val tvCreacion = rowView.findViewById<TextView>(R.id.tvFundacion)
        val tvPopularidad = rowView.findViewById<TextView>(R.id.tvPopularidad)
        val tvProfesional = rowView.findViewById<TextView>(R.id.tvProfesional)

        // Asigna los datos del atleta a los TextViews
        idDeporte.text= deporte?.getidDeporte().toString()
        tvNombreDeporte.text = deporte?.getnombreDeporte()
        tvCreacion.text = deporte?.getfechaFundacion().toString()
        tvPopularidad.text = deporte?.getpopularidadGlobal().toString()
        tvProfesional.text = deporte?.getnivelCompetitivo().toString()

        return rowView
    }
}


