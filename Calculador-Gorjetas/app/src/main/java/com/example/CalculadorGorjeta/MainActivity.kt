package com.example.CalculadorGorjeta

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.example.CalculadorGorjeta.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

/**
 *  ===============================================================================================
 * Atividade principal do APP, ela gerencia todas as funções de cálulo e arredondamento da gorjeta.
 *  ===============================================================================================
 */
class MainActivity : AppCompatActivity() {

    // Criando binding para facilitar a identificação das views
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflando o layout para utilizar dentro do setcontentView
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Criando nossa própria toolbar
        toolbar = findViewById(R.id.toolbars)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(Color.WHITE)


        // chamando a função calculateTip ao clicar no botão calculateButon
        binding.calculateButton.setOnClickListener { calculateTip() }

        // Escondendo o teclado ao pressionar a tecla ENTER do teclado
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    /** ================================
        FUNÇÃO PARA CALCULAR A GORJETA
        ================================ */
    private fun calculateTip() {
        // Recebendo o valor do EditText
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // Validando se o custo é nulo ou zero.
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // Recebendo a porcentagem de acordo com o que foi escolhido no RadioButton
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calculando a gorjeta
        var tip = tipPercentage * cost

        //Verificando se o botão de arredondar foi selecionado
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            //Pegue o valor da gorjeta e arredonde para um número inteiro
            tip = tip.roundToInt().toDouble()
        }

        // Mostre o valor da gorjeta.
        displayTip(tip)
    }

    /**==================================================
    FUNÇÃO para formatar e mostrar o resultado da gorjeta
     ====================================================*/
    private fun displayTip(tip: Double) {
        //Formatando a string para mostrar o valor em Reais.
        val locale: Locale = Locale("pt", "BR")
        val formattedTip = NumberFormat.getCurrencyInstance(locale).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    /**==========================================================
     * FUNÇÃO para esconder o teclado ao pressionar a tecla ENTER.
       ==========================================================*/
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            //Chamando a função de cálculo ao pressionar ENTER no teclado
            calculateTip()
            return true
        }
        return false
    }
}