package tallerwapo.taller_interfaz

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TALLER WAPO !!!",
        state = rememberWindowState(
            width = 2000.dp,
            height = 1000.dp
        )
    ) {
        App()
    }
}