package tallerwapo.taller_interfaz.pantallas.principal.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.themes.AppThemeProvider


object BarraInferior {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    @Composable
    fun Contenido() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(theme.surfaceColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Barra de estado",
                style = theme.bodyText
            )
        }
    }
}
