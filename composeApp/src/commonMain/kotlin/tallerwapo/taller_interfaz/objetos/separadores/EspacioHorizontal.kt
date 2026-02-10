package tallerwapo.taller_interfaz.objetos.separadores

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

@Composable
fun EspacioHorizontal(theme: AppTheme) {
    Spacer(Modifier.width(theme.paddingL))
}