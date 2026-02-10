package tallerwapo.taller_interfaz.objetos.separadores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

@Composable
fun SeparadorHorizontal(theme: AppTheme) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(theme.inputBackgroundColor)
    )
}