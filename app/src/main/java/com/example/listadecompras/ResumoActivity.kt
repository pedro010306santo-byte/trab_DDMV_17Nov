package com.example.listadecompras

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResumoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)
        applyInsets()

        findViewById<TextView>(R.id.resultado).text = intent.getStringExtra("resumo").orEmpty()
        findViewById<Button>(R.id.voltar).setOnClickListener { finish() }
    }
}
