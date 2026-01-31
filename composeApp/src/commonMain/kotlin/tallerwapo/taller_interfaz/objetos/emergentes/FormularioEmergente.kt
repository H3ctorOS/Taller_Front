package tallerwapo.taller_interfaz.objetos.emergentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.window.Dialog
import tallerwapo.taller_interfaz.InterfazContext

@Composable
fun FormularioEmergente(
    mostrar: Boolean,
    onCerrar: () -> Unit,
    contenido: @Composable () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    if (mostrar) {
        Dialog(onDismissRequest = onCerrar) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(theme.surfaceColor, theme.cornerRadius)
                    .padding(theme.paddingM)
            ) {
                contenido()
            }
        }
    }
}