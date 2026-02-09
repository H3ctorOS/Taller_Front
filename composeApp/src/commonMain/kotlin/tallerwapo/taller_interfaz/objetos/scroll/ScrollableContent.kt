package tallerwapo.taller_interfaz.objetos.scroll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScrollableContent(content: @Composable () -> Unit) {
    val scrollState = rememberScrollState()
    Box {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            content()
        }

        // Solo Desktop dibuja la scrollbar
        if (isDesktop()) {
            VerticalScrollbarDesktop(scrollState)
        }
    }
}

// Multiplataforma
expect fun isDesktop(): Boolean

@Composable
expect fun VerticalScrollbarDesktop(scrollState: ScrollState)
