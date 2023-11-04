package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import logic.analyzeIngredients
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Ul

@Composable
fun App() {
    var ingredients by remember { mutableStateOf("") }

    Text("Wklej skład produktu w poniższe pole:")
    Br {}

    TextArea(value = ingredients) {
        onInput {
            println("Input")
            ingredients = it.value
        }
    }
    Br {}

    Ul {
        analyzeIngredients(ingredients).forEach { (ingredient, isXenoestrogen) ->
            Li {
                Text("$ingredient: $isXenoestrogen")
            }
        }
    }
}
