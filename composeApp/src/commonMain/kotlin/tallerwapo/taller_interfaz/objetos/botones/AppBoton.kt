package tallerwapo.taller_interfaz.objetos.botones


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.themes.AppThemeProvider


@Composable
fun  AppBoton(
text: String,
onClick: () -> Unit,
modifier: Modifier = Modifier,
enabled: Boolean = true
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = theme.cornerRadius,
        colors = ButtonDefaults.buttonColors(
            containerColor = theme.buttonBackground,
            contentColor = theme.buttonContent,
            disabledContainerColor = theme.buttonSecondary,
            disabledContentColor = theme.buttonContent
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = theme.paddingM,
            vertical = theme.paddingS
        )
    ) {
        Text(
            text = text,
            style = theme.botonText,
        )
    }
}