package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.petuska.kmdc.banner.Actions
import dev.petuska.kmdc.banner.Content
import dev.petuska.kmdc.banner.Graphic
import dev.petuska.kmdc.banner.Icon
import dev.petuska.kmdc.banner.MDCBanner
import dev.petuska.kmdc.banner.PrimaryAction
import dev.petuska.kmdc.textfield.MDCTextArea
import dev.petuska.kmdc.typography.MDCBody1
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import logic.analyzeIngredients
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.compose.web.dom.Text as ComposeText
import dev.petuska.kmdc.banner.Text as BannerText

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
    MDCBanner(open = true, attrs = { style { backgroundColor(Color.pink) } }) {
        Content {
            Graphic {
                Icon(attrs = { mdcIcon() }) { ComposeText(MDCIcon.Warning.type) }
            }
            BannerText("Aplikacja niegotowa do publikacji, w trakcie budowy")
        }
        Actions {
            PrimaryAction("OK")
        }
    }

    MDCBody1("Wklej skład produktu w poniższe pole:")
}

@Composable
fun IngredientsInput(value: String, onChange: (String) -> Unit) {
    MDCTextArea(
        value = value,
    ) {
        onInput { onChange(it.value) }
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
