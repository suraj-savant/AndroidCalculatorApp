package com.example.helloandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloandroid.ui.theme.HelloAndroidTheme
import  androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                   CalculatorApp()
                }
            }
        }
    }
}


@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Display(input = input)

        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        Column (
            modifier = Modifier.fillMaxHeight(fraction = 1f
            ),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        CalculatorButton(label = label) {
                            input = handleInput(label, input)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Display(input: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.5f)
            .background(Color.DarkGray)
            .padding(16.dp)
            ,

        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = input,
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Right
        )
    }
}

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (label in listOf("/", "*", "-", "+", "=")) Color(0xFF3265c9) else Color(0xFF262926)
        )
    ) {
        Text(

            text = label,
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)

        )
    }
}

fun handleInput(label: String, input: String): String {
    return when (label) {
        "C" -> "" // Clear input
        "=" -> calculateResult(input) // Calculate result
        else -> input + label // Append number or operator
    }
}

fun calculateResult(input: String): String {
    return try {
        val result = evaluateExpression(input)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

fun evaluateExpression(expression: String): Double {
    // A simple evaluation using JavaScript engine
    val expressionReplaced = expression.replace('×', '*').replace('÷', '/')
    return when {
        expressionReplaced.contains('+') -> {
            val (left, right) = expressionReplaced.split('+')
            evaluateExpression(left) + evaluateExpression(right)
        }
        expressionReplaced.contains('-') -> {
            val (left, right) = expressionReplaced.split('-')
            evaluateExpression(left) - evaluateExpression(right)
        }
        expressionReplaced.contains('*') -> {
            val (left, right) = expressionReplaced.split('*')
            evaluateExpression(left) * evaluateExpression(right)
        }
        expressionReplaced.contains('/') -> {
            val (left, right) = expressionReplaced.split('/')
            evaluateExpression(left) / evaluateExpression(right)
        }
        else -> expressionReplaced.toDouble()
    }
}

@Preview
@Composable
fun GreetingPreview() {
            CalculatorApp()


}
