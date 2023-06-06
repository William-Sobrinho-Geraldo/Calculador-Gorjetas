package com.will.CalculadorGorjeta.controller

import com.will.CalculadorGorjeta.view.MainActivity
import kotlin.math.roundToInt

class Controller {
    private val view = MainActivity()

    fun calcularGorjetaController(
        porcentagemDaGorjetaEscolhida: Double = 0.0,
        valorDaContaDigitado: String,
        arredondar: Boolean,
    ) {

        // Recebendo o valor do EditText
        // val stringInTextField = binding.costOfServiceEditText.text.toString() //valor da conta
        val valorTotalDaConta = valorDaContaDigitado.toDoubleOrNull()

        // Validando se o custo é nulo ou zero.
        if (valorTotalDaConta == null || valorTotalDaConta == 0.0) {
            view.mostrarGorjeta(0.0)
            return
        }

        // Calculando a gorjeta
        val tipPercentage = porcentagemDaGorjetaEscolhida / 100
        var gorjeta = tipPercentage * valorTotalDaConta

        //Verificando se o botão de arredondar foi selecionado
        //val roundUp = binding.roundUpSwitch.isChecked
        if (arredondar) {
            //Pegue o valor da gorjeta e arredonde para um número inteiro
            gorjeta = gorjeta.roundToInt().toDouble()
        }

        // Mostre o valor da gorjeta.
        view.mostrarGorjeta(gorjeta)
    }
}