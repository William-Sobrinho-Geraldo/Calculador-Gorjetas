package com.example.CalculadorGorjeta

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.TextView
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

    private val TAG = "Testes de log"
    private lateinit var seekbar : SeekBar
    private lateinit var seekBarPercentage : TextView

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

        // Sincronizando a seekBar
        seekbar = binding.seekBar
        seekBarPercentage = binding.seekBarPercentage
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarPercentage.text = progress.toString()
                Log.i(TAG, "a porcentagem atual é ${seekBarPercentage.text}")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {  }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBarPercentage.let {
                    seekBarPercentage.text = seekBar?.progress.toString()
                }
            }
        })

        // chamando a função calculateTip ao clicar no botão calculateButon
        binding.calculateButton.setOnClickListener { calculateTip(percentage = seekBarPercentage.text.toString().toDouble() )}

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
    private fun calculateTip( percentage : Double = 0.0 ) {
        // Recebendo o valor do EditText
        val stringInTextField = binding.costOfServiceEditText.text.toString() //valor da conta
        val cost = stringInTextField.toDoubleOrNull()

        // Validando se o custo é nulo ou zero.
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // Calculando a gorjeta
        val tipPercentage = percentage / 100
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
            calculateTip(percentage = seekBarPercentage.text.toString().toDouble())
            return true
        }
        return false
    }
}