package tallerwapo.taller_interfaz.objetos.CampoEntrada


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun CampoEntradaRow(
    titulo: String,
    valor: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.PaddingS)
    ) {

        // ───────── Etiqueta ─────────
        AppTextos(
            text = titulo,
            style = AppTheme.Body
        )

        Spacer(Modifier.height(AppTheme.PaddingS))

        // ───────── Campo de entrada ─────────
        TextField(
            value = valor,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = AppTheme.Input,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppTheme.InputBackground,
                unfocusedContainerColor = AppTheme.InputBackground,
                disabledContainerColor = AppTheme.InputBackground,
            ),
            shape = AppTheme.CornerRadius
        )

    }
}