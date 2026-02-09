package tallerwapo.taller_interfaz.objetos.scroll

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable

actual fun isDesktop(): Boolean = false

@Composable
actual fun VerticalScrollbarDesktop(scrollState: ScrollState) {
    // Android no necesita scrollbar manual
}
