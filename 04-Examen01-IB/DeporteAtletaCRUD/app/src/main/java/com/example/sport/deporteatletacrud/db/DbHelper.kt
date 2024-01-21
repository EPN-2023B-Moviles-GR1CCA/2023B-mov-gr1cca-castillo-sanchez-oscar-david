package com.example.sport.deporteatletacrud.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(context: Context?): SQLiteOpenHelper(context,"DBExamen1.db",null,3) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaDeporte =
            "CREATE TABLE t_deporte("+
                    "id_deporte INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nombre_deporte TEXT ,"+
                    "nivel_competitivo TEXT ,"+
                    "fecha_Fundacion TEXT ,"+
                    "popularidad_global DOUBLE );"

        val scriptSQLCrearTablaAtleta =
            "CREATE TABLE t_atleta("+
                    "id_atleta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nombre_atleta TEXT NOT NULL,"+
                    "edad_atleta TEXT not null,"+
                    "deporte TEXT not null,"+
                    "altura TEXT not null);"

        db?.execSQL(scriptSQLCrearTablaDeporte)
        db?.execSQL(scriptSQLCrearTablaAtleta)
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            db?.execSQL("ALTER TABLE t_deporte ADD COLUMN popularidad_global DOUBLE NOT NULL DEFAULT 0")
        }
    }


}