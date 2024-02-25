package com.example.sport.pinterestrv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sport.pinterestrv.databinding.ActivityImageDetailBinding // Asegúrate de que este import coincide con el nombre de tu archivo de layout

class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("IMAGE_URL")
        imageUrl?.let {
            Glide.with(this).load(it).into(binding.selectedImageView) // Acceso correcto mediante View Binding
        }
    }
}