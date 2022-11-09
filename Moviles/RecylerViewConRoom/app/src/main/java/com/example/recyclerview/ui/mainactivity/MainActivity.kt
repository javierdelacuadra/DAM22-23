package com.example.recyclerview.ui.mainactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.ui.common.Constantes
import com.example.recyclerview.ui.listactivity.ListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            verListaButton?.setOnClickListener {
                verLista()
            }
            imageView?.load(Uri.parse(Constantes.URL)) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                transformations(coil.transform.CircleCropTransformation())
            }
        }
    }

    private fun verLista() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}