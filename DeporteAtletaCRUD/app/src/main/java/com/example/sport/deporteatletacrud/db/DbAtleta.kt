package com.example.sport.deporteatletacrud.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DbAtleta(
    private var idAtleta: Int?,
    private var nombreAtleta: String,
    private var edadAtleta: Int,
    private var deporte: Int,
    private var altura: Double,
    private val context: Context
) {


    init {
        idAtleta
        nombreAtleta
        edadAtleta
        deporte
        altura
    }

    fun setidAtleta(idAtleta: Int?){
        this.idAtleta=idAtleta
    }

    fun setnombreAtleta(nombreAtleta: String){
        this.nombreAtleta=nombreAtleta
    }
    fun setedadAtleta(edadAtleta: Int){
        this.edadAtleta= edadAtleta
    }
    fun setdeporte(deporte: Int){
        this.deporte=deporte
    }
    fun setaltura(altura: Double){
        this.altura=altura
    }

    fun getidAtleta(): Int? {
        return this.idAtleta
    }

    fun getnombreAtleta(): String? {
        return this.nombreAtleta
    }

    fun getedadAtleta(): Int? {
        return this.edadAtleta
    }

    fun getdeporte(): Int? {
        return this.deporte
    }

    fun getaltura(): Double? {
        return this.altura
    }


    fun insertAtleta(): Long {
        val dbHelper=DbHelper(this.context)
        val db: SQLiteDatabase =dbHelper.writableDatabase
        val values = ContentValues()
        values.put("nombre_atleta", this.nombreAtleta)
        values.put("edad_atleta", this.edadAtleta.toString())
        values.put("deporte", this.deporte.toString())
        values.put("altura", this.altura.toString())

        return db.insert("t_atleta",null,values)
    }


    @SuppressLint("Range")
    fun showAtletas(id: Int): ArrayList<DbAtleta> {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var listaAtletas = ArrayList<DbAtleta>()
        var atleta: DbAtleta
        var cursorAtleta: Cursor? = null

        // Ver si el id: Int es diferente de null
        cursorAtleta = db.rawQuery("SELECT * FROM t_atleta WHERE deporte=${id+1}", null)

        if (cursorAtleta.moveToFirst()) {
            do {
                var atleta= DbAtleta(null,"",0,0,0.0,this.context)

                atleta.setidAtleta(cursorAtleta.getString(0).toInt())
                atleta.setnombreAtleta(cursorAtleta.getString(1))
                atleta.setedadAtleta(cursorAtleta.getString(2).toInt())
                atleta.setdeporte(cursorAtleta.getString(3).toInt())
                atleta.setaltura(cursorAtleta.getString(4).toDouble())
                listaAtletas.add(atleta)
            } while (cursorAtleta.moveToNext())
        }

        cursorAtleta.close()
        return listaAtletas
    }

    fun getAtletaById(id: Int): DbAtleta{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        println(id)
        var atleta= DbAtleta(null,"",0,0,0.0,this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_atleta WHERE id_atleta = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                atleta.setidAtleta(Integer.parseInt(cursor.getString(0)) )
                atleta.setnombreAtleta(cursor.getString(1))
                atleta.setedadAtleta(cursor.getString(2).toString().toInt())
                atleta.setdeporte(cursor.getString(3).toString().toInt())
                atleta.setaltura(cursor.getString(4).toString().toDouble())
            } while (cursor.moveToNext())
        }

        cursor.close()
        return atleta
    }
    fun updateAtleta(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre_atleta", this.nombreAtleta)
        values.put("edad_atleta", this.edadAtleta.toString())
        values.put("deporte", this.deporte.toString())
        values.put("altura", this.altura.toString())

        return db.update("t_atleta", values, "id_atleta="+this.idAtleta, null)
    }
    fun deleteAtleta(id: Int): Int{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete("t_atleta", "id_atleta="+(id+1), null)
    }

    override fun toString(): String {
        val texto=
            "id Atleta: ${idAtleta}\n"+
                    "Atleta: ${nombreAtleta}\n"+
                    "Edad: ${edadAtleta}\n"+
                    "Deporte: ${deporte}\n"+
                    "Altura: ${altura}\n"
        return texto
    }
}