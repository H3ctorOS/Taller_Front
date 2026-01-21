package tallerwapo.taller_interfaz

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "taller_interfaz",
    ) {
        App()
    }
}