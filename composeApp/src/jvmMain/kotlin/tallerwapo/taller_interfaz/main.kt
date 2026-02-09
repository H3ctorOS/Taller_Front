package tallerwapo.taller_interfaz

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import tallerwapo.core.servicios.ConfigServices

fun main() = application {
    // --- Inicializar Config ---
    ConfigServices.inicializar()  // carga IP y MAC o crea archivo con valores por defecto

    Window(
        onCloseRequest = ::exitApplication,
        title = "TALLER WAPO !!!",
        state = rememberWindowState(
            width = 2000.dp,
            height = 1300.dp
        )
    ) {
        App()
    }
}