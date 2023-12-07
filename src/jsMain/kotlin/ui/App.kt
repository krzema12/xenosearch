package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data.xenoestrogens
import dev.petuska.kmdc.data.table.Body
import dev.petuska.kmdc.data.table.Cell
import dev.petuska.kmdc.data.table.Container
import dev.petuska.kmdc.data.table.MDCDataTable
import dev.petuska.kmdc.data.table.MDCDataTableHeader
import dev.petuska.kmdc.data.table.Row
import dev.petuska.kmdc.dialog.Action
import dev.petuska.kmdc.dialog.Actions
import dev.petuska.kmdc.dialog.CloseButton
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.Header
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.Title
import dev.petuska.kmdc.dialog.onClosed
import dev.petuska.kmdc.elevation.MDCElevation
import dev.petuska.kmdc.textfield.MDCTextArea
import dev.petuska.kmdc.typography.MDCBody1
import dev.petuska.kmdc.typography.MDCBody2
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import logic.analyzeIngredients
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text as ComposeText

@Composable
fun App() {
    var ingredients by remember { mutableStateOf("") }
    var warningDialogOpen by remember { mutableStateOf(true) }

    WorkInProgressWarning(warningDialogOpen, onClosed = { warningDialogOpen = false })

    MDCElevation(
        z = 5,
        attrs = {
            style {
                maxWidth(900.px)
                padding(20.px)
                property("margin", "20px auto")
            }
        },
    ) {
        Column {
            Header()
            IngredientsInput(value = ingredients, onChange = { ingredients = it })
            Results(ingredients)
        }
    }
}

@Composable
private fun WorkInProgressWarning(warningDialogOpen: Boolean, onClosed: () -> Unit) {
    MDCDialog(
        open = warningDialogOpen,
        fullscreen = true,
        stacked = false,
        attrs = {
            onClosed { onClosed() }
        },
    ) {
        Header {
            Title("Ostrzeżenie")
            CloseButton(MDCIcon.Close.type, attrs = { mdcIcon() })
        }
        Content {
            ComposeText("Aplikacja niegotowa do publikacji, w trakcie budowy!")
        }
        Actions {
            Action(action = "ok", text = "OK")
        }
    }
}

@Composable
private fun Header() {
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
    MDCBody2(
        """
        Wyszukiwarka weryfikuje skład pod kątem ${xenoestrogens.size} ksenoestrogenów (pełna lista poniżej). Nie
        stanowi to pełnej bazy ksenoestrogenów występującej w kosmetykach. Wyszukiwarka wciąż jest aktualizowana o nowe
        ksenoestrogeny.
        """.trimIndent(),
    )

    MDCBody2("Znalezione ksenoestrogeny przenoszone są na początek listy i oznaczane kolorem czerwonym.")

    MDCDataTable {
        Container {
            MDCDataTableHeader {
                Cell(text = "Ksenoestrogen", attrs = { style { fontWeight("bold") } })
                Cell(text = "Wynik", attrs = { style { fontWeight("bold") } })
            }
            Body {
                analyzeIngredients(ingredients)
                    .entries
                    .sortedWith(compareBy({ !it.value }, { it.key.lowercase() }))
                    .forEach { (knownXenoestrogen, isPresent) ->
                        Row(attrs = {
                            style {
                                if (isPresent) {
                                    backgroundColor(rgb(255, 100, 100))
                                }
                            }
                        }) {
                            Cell(text = knownXenoestrogen)
                            val resultText = if (isPresent) "Znaleziono" else "Nie znaleziono"
                            Cell(text = resultText)
                        }
                    }
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
