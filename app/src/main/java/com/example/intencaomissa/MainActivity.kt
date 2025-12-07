package com.example.intencaomissa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intencaomissa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNovaIntencao.setOnClickListener {
            startActivity(Intent(this, CadastroIntencaoActivity::class.java))
        }

        binding.btnListaIntencoes.setOnClickListener {
            startActivity(Intent(this, ListaIntencoesActivity::class.java))
        }
    }
}
