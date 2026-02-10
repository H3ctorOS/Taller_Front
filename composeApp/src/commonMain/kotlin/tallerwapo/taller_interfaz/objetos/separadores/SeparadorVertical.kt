package tallerwapo.taller_interfaz.objetos.separadores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

@Composable
fun SeparadorVertical(theme: AppTheme) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(theme.inputBackgroundColor)
    )
}