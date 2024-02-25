package com.example.sport.pinterestrv

import PinterestAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sport.pinterestrv.ui.theme.PinterestRVTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_main)
        val imageUrls = listOf("https://www.elmueble.com/medio/2023/02/08/25-recetas-de-tupper-para-preparar-el-dia-antes_afc5e2b6_230208170301_900x900.jpg","https://st4allthings4p4ci.blob.core.windows.net/allthingshair/allthingshair/wp-content/uploads/sites/13/2023/11/44757/corte-de-pelo-desvanecido-377x566.jpg","https://i.pinimg.com/236x/5b/41/5b/5b415b159b9a709bb0ea4075de0702bd.jpg","https://i.pinimg.com/236x/60/e4/d0/60e4d0b36f266e108b8701b983b33382.jpg","https://i.pinimg.com/736x/c8/ab/61/c8ab614eb7f602cf471e1fdefd06f8a1.jpg","https://i.pinimg.com/564x/10/e3/0c/10e30c64724edeff5bdc1e5b5f296f16.jpg","https://i.pinimg.com/736x/98/97/fd/9897fd7d57a93e16642b8f20419ec508.jpg")
        val recyclerView: RecyclerView = findViewById(R.id.pinterestRecyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = PinterestAdapter(imageUrls)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PinterestRVTheme {
        Greeting("Android")
    }
}