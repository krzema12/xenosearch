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
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Ul

@Composable
fun App() {
    var ingredients by remember { mutableStateOf("") }

    Column {
        Header()
        IngredientsInput(value = ingredients, onChange = { ingredients = it })
        Results(ingredients)
    }
}

@Composable
private fun Header() {
    Div(attrs = { style { color(Color.red) } }) {
        Text("Aplikacja niegotowa do publikacji, w trakcie budowy")
    }

    Text("Wklej skład produktu w poniższe pole:")
}

@Composable
fun IngredientsInput(value: String, onChange: (String) -> Unit) {
    TextArea(value = value) {
        rows(20)
        cols(80)
        onInput {
            onChange(it.value)
        }
    }
}

@Composable
private fun Results(ingredients: String) {
    Ul {
        analyzeIngredients(ingredients)
            .entries
            .sortedWith(compareBy({ !it.value }, { it.key.lowercase() }))
            .forEach { (knownXenoestrogen, isPresent) ->
                Li {
                    val resultMarker = if (isPresent) "⚠\uFE0F" else "⚪"
                    Text("$knownXenoestrogen: $resultMarker")
                }
            }
    }
}

@Composable
private fun Column(content: @Composable () -> Unit) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            height(100.percent)
            margin(0.px)
            boxSizing("border-box")
        }
    }) {
        content()
    }
}
