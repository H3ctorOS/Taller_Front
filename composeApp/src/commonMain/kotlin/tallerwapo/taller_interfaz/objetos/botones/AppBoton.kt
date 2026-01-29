package tallerwapo.taller_interfaz.objetos.botones


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.themes.AppTheme


@Composable
fun  AppBoton(
text: String,
onClick: () -> Unit,
modifier: Modifier = Modifier,
enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = AppTheme.CornerRadius,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.ButtonBackground,
            contentColor = AppTheme.ButtonContent,
            disabledContainerColor = AppTheme.ButtonSecondary,
            disabledContentColor = AppTheme.ButtonContent
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = AppTheme.PaddingM,
            vertical = AppTheme.PaddingS
        )
    ) {
        Text(
            text = text,
            style = AppTheme.Body
        )
    }
}