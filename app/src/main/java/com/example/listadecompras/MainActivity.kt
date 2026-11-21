package com.example.listadecompras

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyInsets()

        val nome = findViewById<EditText>(R.id.nome)
        val itens = listOf(R.id.arroz, R.id.feijao, R.id.leite, R.id.pao)
            .map { findViewById<CheckBox>(it) }
        val prioridades = findViewById<RadioGroup>(R.id.prioridades)
        val resultado = findViewById<TextView>(R.id.resultado)
        val abrir = findViewById<Button>(R.id.abrir)

        resultado.text = savedInstanceState?.getString("resumo").orEmpty()
        abrir.isEnabled = resultado.text.isNotEmpty()

        findViewById<Button>(R.id.enviar).setOnClickListener {
            resultado.text = ""
            abrir.isEnabled = false
            val titulo = nome.text.toString().trim()
            if (titulo.isEmpty()) {
                nome.error = "Digite o nome da lista"
                nome.requestFocus()
                return@setOnClickListener
            }
            val selecionados = itens.filter { it.isChecked }.joinToString(", ") { it.text }
            if (selecionados.isEmpty()) {
                resultado.text = "Marque pelo menos um item."
                return@setOnClickListener
            }
            val prioridade = findViewById<RadioButton>(prioridades.checkedRadioButtonId)
            resultado.text = "Lista: $titulo\nItens: $selecionados\nPrioridade: ${prioridade.text}"
            abrir.isEnabled = true
        }

        abrir.setOnClickListener {
            val intent = Intent(this, ResumoActivity::class.java)
            intent.putExtra("resumo", resultado.text.toString())
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (findViewById<Button>(R.id.abrir).isEnabled) {
            outState.putString("resumo", findViewById<TextView>(R.id.resultado).text.toString())
        }
    }
}

fun AppCompatActivity.applyInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tela)) { view, insets ->
        val edges = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        view.setPadding(edges.left, edges.top, edges.right, edges.bottom)
        insets
    }
}
