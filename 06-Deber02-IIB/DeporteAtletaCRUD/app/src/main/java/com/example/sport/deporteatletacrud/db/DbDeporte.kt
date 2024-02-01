package com.example.sport.deporteatletacrud.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.time.format.DateTimeFormatter

class DbDeporte(
    private var idDeporte: Int?,
    private var nombreDeporte: String,
    private var fechaFundacion: String,
    private var popularidadGlobal: Double,
    private var nivelCompetitivo: Boolean,
    private val context: Context
) {


    init {
        idDeporte
        nombreDeporte
        fechaFundacion
        popularidadGlobal
        nivelCompetitivo
    }

    fun setidDeporte(idDeporte: Int){
        this.idDeporte=idDeporte
    }

    fun setnombreDeporte(nombreDeporte: String){
        this.nombreDeporte=nombreDeporte
    }
    fun setfechaFundacion(fechaFundacion: String){
        this.fechaFundacion= fechaFundacion
    }
    fun setpopularidadGlobal(popularidadGlobal: Double){
        this.popularidadGlobal=popularidadGlobal
    }
    fun setnivelCompetitivo(nivelCompetitivo: Boolean){
        this.nivelCompetitivo=nivelCompetitivo
    }

    fun getidDeporte(): Int? {
        return this.idDeporte
    }

    fun getnombreDeporte(): String? {
        return this.nombreDeporte
    }

    fun getfechaFundacion(): String? {
        return this.fechaFundacion
    }

    fun getpopularidadGlobal(): Double? {
        return this.popularidadGlobal
    }

    fun getnivelCompetitivo(): Boolean? {
        return this.nivelCompetitivo
    }


    fun insertDeporte(): Long {
        val dbHelper=DbHelper(this.context)
        val db: SQLiteDatabase =dbHelper.writableDatabase
        println(this.nivelCompetitivo)
        val values = ContentValues()
        values.put("nombre_deporte", this.nombreDeporte)
        values.put("nivel_competitivo", this.nivelCompetitivo.toString())
        values.put("fecha_fundacion", this.fechaFundacion)
        values.put("popularidad_global", this.popularidadGlobal)

        return db.insert("t_deporte",null,values)
    }


    fun showDeportes(): ArrayList<DbDeporte>{
        val dbHelper: DbHelper = DbHelper(this.context)
        val db : SQLiteDatabase= dbHelper.writableDatabase

        var lista=ArrayList<DbDeporte>()
        var deporte: DbDeporte
        var cursor: Cursor? = null
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        cursor= db.rawQuery("SELECT * FROM t_deporte", null)
        if (cursor.moveToFirst()){
            do {
                deporte= DbDeporte(null,"","",0.0,true,this.context)
                deporte.setidDeporte(Integer.parseInt(cursor.getString(0)) )
                deporte.setnombreDeporte(cursor.getString(1))
                deporte.setnivelCompetitivo(cursor.getString(2).toString().toBoolean())
                deporte.setfechaFundacion(cursor.getString(3))
                deporte.setpopularidadGlobal(cursor.getString(4).toString().toDouble())
                lista.add(deporte)

            }while (cursor.moveToNext())

        }
        cursor.close()
        return lista
    }


    fun getDeporteById(id: Int): DbDeporte{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var deporte = DbDeporte(null, "", "", 0.0, false, this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_deporte WHERE id_deporte = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                deporte.setidDeporte(cursor.getString(0).toInt())
                deporte.setnombreDeporte(cursor.getString(1))
                deporte.setnivelCompetitivo(cursor.getString(2).toBoolean())
                deporte.setfechaFundacion(cursor.getString(3))
                deporte.setpopularidadGlobal(cursor.getString(4).toDouble())
            } while (cursor.moveToNext())
        }

        cursor.close()
        return deporte
    }
    fun updateDeporte(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre_deporte", this.nombreDeporte)
        values.put("nivel_competitivo", this.nivelCompetitivo.toString())
        values.put("fecha_fundacion", this.fechaFundacion)
        values.put("popularidad_global", this.popularidadGlobal)

        return db.update("t_deporte", values, "id_deporte="+this.idDeporte, null)
    }
    fun deleteDeporte(id: Int): Int{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        return db.delete("t_deporte", "id_deporte="+(id+1), null)
    }

    override fun toString(): String {
        val texto=
            "id Deporte: ${idDeporte}\n"+
                    "Deporte: ${nombreDeporte}\n"+
                    "Fundación: ${fechaFundacion}\n"+
                    "Popularidad: ${popularidadGlobal}\n"+
                    "Es Profesional?: ${nivelCompetitivo}\n"
        return texto
    }
}