package tallerwapo.taller_interfaz.objetos.textos

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

@Composable
fun ItemTexto(
    titulo: String,
    valor: String?,
    theme: AppTheme
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTextos(
            text = "$titulo ",
            style = theme.bodyText
        )

        AppTextos(
            text = valor ?: "",
            style = theme.bodyText
        )
    }
}
