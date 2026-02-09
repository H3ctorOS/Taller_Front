package tallerwapo.taller_interfaz.objetos.scroll

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box

@Composable
actual fun VerticalScrollbarDesktop(scrollState: ScrollState) {
    // NOTA: este Box se pone para usar align() correctamente
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.CenterEnd
    ) {
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}

actual fun isDesktop(): Boolean = true
