package tallerwapo.taller_interfaz.objetos.botones


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.themes.AppThemeProvider


@Composable
fun  MasBoton(
onClick: () -> Unit,
modifier: Modifier = Modifier,
enabled: Boolean = true
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Button(
        onClick = onClick,
        modifier = modifier.sizeIn(minWidth = 45.dp, minHeight = 10.dp), // 🔹 Quita mínimo Material
        enabled = enabled,
        shape = theme.cornerRadius,
        contentPadding = PaddingValues(1.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = theme.buttonBackground,
            contentColor = theme.buttonContent,
            disabledContainerColor = theme.buttonSecondary,
            disabledContentColor = theme.buttonContent
        )
    ) {
        Text(
            text = "+",
            style = theme.botonText,
            modifier = Modifier.padding(0.dp)
        )
    }
}