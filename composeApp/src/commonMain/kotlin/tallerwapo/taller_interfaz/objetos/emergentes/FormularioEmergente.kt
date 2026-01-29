package tallerwapo.taller_interfaz.objetos.emergentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.themes.AppTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.window.Dialog

@Composable
fun FormularioEmergente(
    mostrar: Boolean,
    onCerrar: () -> Unit,
    contenido: @Composable () -> Unit
) {

    if (mostrar) {
        Dialog(onDismissRequest = onCerrar) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.Surface, AppTheme.CornerRadius)
                    .padding(AppTheme.PaddingM)
            ) {
                contenido()
            }
        }
    }
}