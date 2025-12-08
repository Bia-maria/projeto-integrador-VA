package com.example.intencaomissa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.intencaomissa.databinding.ActivityCadastroIntencaoBinding

class CadastroIntencaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroIntencaoBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroIntencaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        configurarSpinner()
        configurarBotao()
    }

    private fun configurarSpinner() {
        val tipos = listOf(
            "Pela alma",
            "Saúde",
            "Aniversário",
            "Ação de graças"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerTipo.adapter = adapter
    }

    private fun configurarBotao() {

        binding.btnSalvar.setOnClickListener {

            val nome = binding.edtNome.text.toString().trim()
            val intencao = binding.edtIntencao.text.toString().trim()
            val tipo = binding.spinnerTipo.selectedItem.toString()
            val data = binding.edtData.text.toString().trim()

            // validação
            var erro = false

            if (nome.isEmpty()) {
                binding.edtNome.error = "Obrigatório"
                erro = true
            }

            if (intencao.isEmpty()) {
                binding.edtIntencao.error = "Obrigatório"
                erro = true
            }

            if (data.isEmpty()) {
                binding.edtData.error = "Obrigatório"
                erro = true
            }

            if (erro) return@setOnClickListener

            // salva no banco
            val sucesso = dbHelper.inserirIntencao(nome, intencao, tipo, data)

            if (sucesso) {
                Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
