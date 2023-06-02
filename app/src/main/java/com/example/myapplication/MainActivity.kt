package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var operand1: Double = 0.0
    private var operator: String? = null
    private var clearInput: Boolean = false
    private var clearResult: Boolean = false
    private var lastPressedWasOperator: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Achei no Google para ocultar a barra de status do Android
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir os listeners para os botões numéricos
        binding.button0.setOnClickListener { numberPressed("0") }
        binding.button1.setOnClickListener { numberPressed("1") }
        binding.button2.setOnClickListener { numberPressed("2") }
        binding.button3.setOnClickListener { numberPressed("3") }
        binding.button4.setOnClickListener { numberPressed("4") }
        binding.button5.setOnClickListener { numberPressed("5") }
        binding.button6.setOnClickListener { numberPressed("6") }
        binding.button7.setOnClickListener { numberPressed("7") }
        binding.button8.setOnClickListener { numberPressed("8") }
        binding.button9.setOnClickListener { numberPressed("9") }

        // Definir os listeners para os botões de operação
        binding.buttonAdd.setOnClickListener { operatorPressed("+") }
        binding.buttonSubtract.setOnClickListener { operatorPressed("-") }
        binding.buttonMultiply.setOnClickListener { operatorPressed("*") }
        binding.buttonDivide.setOnClickListener { operatorPressed("/") }

        // Definir o listener para o botão de vírgula
        binding.buttonDot.setOnClickListener { dotPressed() }

        // Definir o listener para o botão de igual
        binding.buttonEquals.setOnClickListener { calculateResult() }

        // Definir o listener para o botão de limpar
        binding.buttonClear.setOnClickListener { clearCalculator() }
    }

    private fun numberPressed(number: String) {
        // verifica se o resultado deve ser limpo
        if(clearResult) {
            binding.textViewResult.text = ""
            clearResult = false
        }

        var currentInput = binding.textViewResult.text.toString()
        if(operator != null && clearInput) {
            currentInput = ""
        }
        binding.textViewResult.text = currentInput + number
        clearInput = false
        lastPressedWasOperator = false

    }

    private fun dotPressed() {
        val currentInput = binding.textViewResult.text.toString()
        if(currentInput.isEmpty()) {
            binding.textViewResult.text = "0."
        } else if (!currentInput.contains(".")) {
            binding.textViewResult.text = currentInput + "."
        }
        lastPressedWasOperator = false
    }

    private fun operatorPressed(operator: String) {
        // Verifica se já houve uma operação antes
        if(binding.textViewInput.text.isNotEmpty() && !lastPressedWasOperator) {
            calculateResult()
        }

        // verifica se há números preenchidos
        if(binding.textViewResult.text.isEmpty()) return

        val currentInput = binding.textViewResult.text.toString()
        operand1 = binding.textViewResult.text.toString().toDouble()

        this.operator = operator
        clearInput = true
        lastPressedWasOperator = true

        binding.textViewInput.text = currentInput + operator
    }

    private fun calculateResult() {
        // verifica se há números preenchidos
        if(binding.textViewInput.text.isEmpty() ||
           binding.textViewResult.text.isEmpty() )
            return

        val operand2 = binding.textViewResult.text.toString().toDouble()
        var result: Double? = null

        when (operator) {
            "+" -> result = operand1 + operand2
            "-" -> result = operand1 - operand2
            "*" -> result = operand1 * operand2
            "/" -> result = operand1 / operand2
        }

        if (result != null) {
            binding.textViewResult.text = result.toString().removeSuffix(".0")
        }

        binding.textViewInput.text = ""
        operand1 = 0.0
        operator = null
        clearInput = true
        clearResult = true
        lastPressedWasOperator = false
    }

    private fun clearCalculator() {
        binding.textViewInput.text = ""
        binding.textViewResult.text = ""
        operand1 = 0.0
        operator = null
        clearInput = false
        clearResult = false
        lastPressedWasOperator = false
    }
}