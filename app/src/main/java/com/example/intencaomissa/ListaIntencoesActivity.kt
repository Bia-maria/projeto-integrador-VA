package com.example.intencaomissa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intencaomissa.databinding.ActivityListaIntencoesBinding

class ListaIntencoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaIntencoesBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaIntencoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        val lista = db.listarIntencoes()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = IntencaoAdapter(lista)
    }
}
