package com.example.myapplication

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var buttonShow: Button
    private lateinit var listView: ListView
    private lateinit var textViewInfo: TextView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.drawable.activity_main)

        editText = findViewById(R.id.editText)
        radioGroup = findViewById(R.id.radioGroup)
        buttonShow = findViewById(R.id.button_show)
        listView = findViewById(R.id.listView)
        textViewInfo = findViewById(R.id.textView_info)

        buttonShow.setOnClickListener {
            val inputText = editText.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val number = inputText.toIntOrNull()
            if (number == null) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val criteria = when (selectedRadioButtonId) {
                R.id.radio_even -> "even"
                R.id.radio_odd -> "odd"
                R.id.radio_square -> "square"
                else -> null
            }

            if (criteria == null) {
                Toast.makeText(this, "Please select a criteria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val results = generateResults(number, criteria)

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
            listView.adapter = adapter

            textViewInfo.text = "Results based on '$criteria' criteria"
        }
    }

    private fun generateResults(number: Int, criteria: String): List<String> {
        val results = mutableListOf<String>()
        for (i in 1..number) {
            when (criteria) {
                "even" -> if (i % 2 == 0) results.add(i.toString())
                "odd" -> if (i % 2 != 0) results.add(i.toString())
                "square" -> if (isPerfectSquare(i)) results.add(i.toString())
            }
        }
        return results
    }

    private fun isPerfectSquare(n: Int): Boolean {
        val sqrt = kotlin.math.sqrt(n.toDouble()).toInt()
        return sqrt * sqrt == n
    }
}
