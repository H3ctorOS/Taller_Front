package tallerwapo.taller_interfaz.objetos.campoEntrada

import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones.Validacion
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun CampoEntradaRow(
    titulo: String,
    valor: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    validaciones: List<Validacion> = emptyList() // <- lista de Validacion
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = theme.paddingS)
    ) {
        // ───────── Etiqueta ─────────
        AppTextos(
            text = titulo,
            style = theme.bodyText
        )

        Spacer(Modifier.height(theme.paddingS))

        // ───────── Campo de entrada ─────────
        TextField(
            value = valor,
            onValueChange = { nuevoValor ->
                onValueChange(nuevoValor)

                // Validar todas las reglas y coger el mensaje de la primera que falle
                val fallo = validaciones.firstOrNull { !it.funcion(nuevoValor) }
                mensajeError = fallo?.mensajeError
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = theme.input,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = theme.inputBackgroundColor,
                unfocusedContainerColor = theme.inputBackgroundColor,
                disabledContainerColor = theme.inputBackgroundColor,
            ),
            shape = theme.cornerRadius,
            enabled = enabled
        )

        // ───────── Mensaje de error ─────────
        mensajeError?.let {
            AppTextos(
                text = it,
                style = theme.errorText
            )
        }
    }
}
