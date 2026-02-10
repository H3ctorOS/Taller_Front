package tallerwapo.taller_interfaz.objetos.botones

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun AppBoton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledColor: androidx.compose.ui.graphics.Color? = null, // color cuando está habilitado
    disabledColor: androidx.compose.ui.graphics.Color? = null // color cuando está deshabilitado
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = theme.cornerRadius,
        colors = ButtonDefaults.buttonColors(
            containerColor = enabledColor ?: theme.buttonBackground,
            contentColor = theme.buttonContent,
            disabledContainerColor = disabledColor ?: theme.buttonSecondary,
            disabledContentColor = theme.buttonContent
        ),
        contentPadding = PaddingValues(
            horizontal = theme.paddingM,
            vertical = theme.paddingS
        )
    ) {
        Text(
            text = text,
            style = theme.botonText
        )
    }
}
