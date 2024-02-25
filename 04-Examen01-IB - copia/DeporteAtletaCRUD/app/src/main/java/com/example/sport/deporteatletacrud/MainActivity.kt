package com.example.sport.deporteatletacrud

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sport.deporteatletacrud.databinding.ActivityMainBinding
import com.example.sport.deporteatletacrud.db.DbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper: DbHelper= DbHelper(this)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        if(db != null){

        }else{
            Toast.makeText(this,"Error al crear la bd",Toast.LENGTH_LONG).show()
        }
        irActividad(Deporte::class.java)

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }



}