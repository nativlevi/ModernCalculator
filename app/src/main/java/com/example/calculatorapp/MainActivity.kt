package com.example.calculatorapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var inputTextView: TextView
    private lateinit var outputTextView: TextView

    private var input : String = ""
    private var opera1 : Double = 0.0
    private var opera2 : Double = 0.0
    private var operator : String = ""



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.input)
        outputTextView = findViewById(R.id.output)

        val buttons = listOf<Button>(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.buttonmul),
            findViewById(R.id.buttondev),
            findViewById(R.id.buttonsub),
            findViewById(R.id.buttonplus),
            findViewById(R.id.buttonper),
            findViewById(R.id.buttonpCA),
            findViewById(R.id.buttonpone),
            findViewById(R.id.buttondel),
            findViewById(R.id.buttondot),
            findViewById(R.id.buttoneq)
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button.text.toString())
            }
        }
    }

    private fun appendInput(value : String){
        input += value
        inputTextView.text = input
    }

    private fun appendDecimal(){
        if(!input.contains(".")){
            input += "."
            inputTextView.text = input
        }
    }

    private fun handleOperator(operator : String){
        this.operator = operator
        opera1 = input.toDouble()
        input = ""
        inputTextView.text = input
    }

    private fun calculateResult() {
        if (input.isNotEmpty()) {
            opera2 = input.toDouble()
            val result = when (operator) {
                "+" -> opera1 + opera2
                "-" -> opera1 - opera2
                "X" -> opera1 * opera2
                "/" -> opera1 / opera2
                else -> throw illegalDecoyCallException("invalid")
            }

            // בדיקה אם התוצאה היא מספר שלם
            val displayResult = if (result % 1 == 0.0) {
                result.toInt().toString() // הצגת התוצאה כמספר שלם
            } else {
                result.toString() // הצגת התוצאה כמספר עשרוני
            }

            outputTextView.text = displayResult
            input = displayResult
            inputTextView.text = input
        }
    }


    private fun clearInput(){
        input = ""
        opera1 = 0.0
        opera2 = 0.0
        operator = ""
        inputTextView.text = input
        outputTextView.text = ""
    }

    private fun hadlePercent(){
        if(input.isNotEmpty()){
            val value = input.toDouble() / 100
            input = value.toString()
            inputTextView.text = input
        }
    }

    private fun toggleSign() {
        if (input.isNotEmpty()) {
            val value = input.toDouble() * -1
            input = value.toString()
            inputTextView.text = input
        }
    }

    private fun String.isNumeric(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun deleteLastCharacter() {
        if (input.isNotEmpty()) {
            // מחיקת התו האחרון מהמחרוזת input
            input = input.substring(0, input.length - 1)
            // עדכון inputTextView עם הערך החדש
            inputTextView.text = input
        }
    }



    private fun handleButtonClick(value: String) {
        when {
            value.isNumeric() -> appendInput(value)
            value == "." -> appendDecimal()
            value in listOf("+", "-", "X", "/") -> handleOperator(value)
            value == "=" -> calculateResult()
            value == "CA" -> clearInput()
            value == "%" -> hadlePercent()
            value == "+/-" -> toggleSign()
            value == "del" -> deleteLastCharacter()
        }
    }






}

