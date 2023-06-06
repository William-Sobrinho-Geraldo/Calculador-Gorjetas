package com.will.CalculadorGorjeta.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.will.CalculadorGorjeta.R
import com.will.CalculadorGorjeta.controller.Controller
import com.will.CalculadorGorjeta.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

/**
 *  ===============================================================================================
 * Activity principal do APP, responsável por enviar inputs ao controller e mostrar o resultado do cáluclo
 *  ===============================================================================================*/

class MainActivity : AppCompatActivity() {

    private val controller = Controller()
    private lateinit var seekbar: SeekBar
    private lateinit var seekBarPorcentagemEscolhida: TextView

    // Criando binding para facilitar a identificação das views
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflando o layout para utilizar dentro do setcontentView
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Criando nossa própria toolbar
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        // Sincronizando as setas com a seekbar
        binding.setaEsquerda.setOnClickListener { diminuirPorcentagem() }
        binding.setaDireita.setOnClickListener { aumentarPorcentagem() }

        // Sincronizando o texto da porcentagem com a seekBar
        seekbar = binding.seekBar
        seekBarPorcentagemEscolhida = binding.seekBarPorcentagem
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarPorcentagemEscolhida.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBarPorcentagemEscolhida.let {
                    seekBarPorcentagemEscolhida.text = seekBar?.progress.toString()
                }
            }
        })

        // Escondendo o teclado ao pressionar a tecla ENTER do teclado
        binding.valorDaConta.setOnKeyListener { view, keyCode, _ ->
            esconderTecladoAoPressionarEnter(view, keyCode)
        }

        // chamando a função calcularGorjetaController ao clicar no botão calcular
        binding.botaoCalcular.setOnClickListener {
            controller.calcularGorjetaController(
                porcentagemDaGorjetaEscolhida = seekBarPorcentagemEscolhida.text.toString()
                    .toDouble(),
                valorDaContaDigitado = binding.valorDaConta.text.toString(),
                arredondar = binding.roundUpSwitch.isChecked,
            )
            //Escondendo o teclado ao clicar em calcular
            val tecladoEscondido =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            tecladoEscondido.hideSoftInputFromWindow(binding.botaoCalcular.windowToken, 0)
        }
    }

    fun mostrarGorjeta(tip: Double) {
        //Formatando a string para mostrar o valor em Reais.
        val local: Locale = Locale("pt", "BR")
        val gorjetaFormatada = NumberFormat.getCurrencyInstance(local).format(tip)
        binding.resultadoDaGorjeta.text = getString(R.string.apresentar_gorjeta, gorjetaFormatada)
    }

    private fun esconderTecladoAoPressionarEnter(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val tecladoEscondido =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            tecladoEscondido.hideSoftInputFromWindow(view.windowToken, 0)
            //Chamando a função de cálculo ao pressionar ENTER no teclado
            controller.calcularGorjetaController(
                porcentagemDaGorjetaEscolhida = seekBarPorcentagemEscolhida.text.toString()
                    .toDouble(),
                valorDaContaDigitado = binding.valorDaConta.text.toString(),
                arredondar = binding.roundUpSwitch.isChecked,
            )
            return true
        }
        return false
    }

    private fun diminuirPorcentagem() {
        var numeroInt: Int = binding.seekBarPorcentagem.text.toString().toInt()
        numeroInt--

        seekBarPorcentagemEscolhida.text = numeroInt.toString()
        seekbar.progress = numeroInt
    }

    private fun aumentarPorcentagem() {
        var numeroInt: Int = binding.seekBarPorcentagem.text.toString().toInt()
        numeroInt++

        seekBarPorcentagemEscolhida.text = numeroInt.toString()
        seekbar.progress = numeroInt
    }
}