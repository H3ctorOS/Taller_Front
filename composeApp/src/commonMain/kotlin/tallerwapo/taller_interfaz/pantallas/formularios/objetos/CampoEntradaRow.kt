package tallerwapo.taller_interfaz.pantallas.formularios.objetos


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun CampoEntradaRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.PaddingS)
    ) {

        // ───────── Etiqueta ─────────
        Text(
            text = label,
            style = AppTheme.Body,
            color = AppTheme.TextoSecundario
        )

        Spacer(Modifier.height(AppTheme.PaddingS))

        // ───────── Campo de entrada ─────────
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = AppTheme.Input,
        )
    }
}