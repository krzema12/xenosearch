import org.jetbrains.compose.web.renderComposable
import ui.App

fun main() {
    renderComposable(rootElementId = "root") {
        App()
    }
}
