package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import logic.analyzeIngredients
import org.jetbrains.compose.web.attributes.cols
import org.jetbrains.compose.web.attributes.rows
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Ul

@Composable
fun App() {
    var ingredients by remember { mutableStateOf("") }

    Div(attrs = { style { color(Color.red) } }) {
        Text("Aplikacja niegotowa do publikacji, w trakcie budowy")
    }

    Text("Wklej skład produktu w poniższe pole:")
    Br {}

    TextArea(value = ingredients) {
        rows(20)
        cols(80)
        onInput {
            println("Input")
            ingredients = it.value
        }
    }
    Br {}

    Ul {
        analyzeIngredients(ingredients).forEach { (knownXenoestrogen, isPresent) ->
            Li {
                Text("$knownXenoestrogen: ${if (isPresent) "⚠\uFE0F" else "⚪" }")
            }
        }
    }
}
