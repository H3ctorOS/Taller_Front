package tallerwapo.taller_interfaz.objetos.campoEntrada


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SeleccionableRow(
    titulo: String,
    items: List<T>,
    seleccionado: T?,
    onSeleccionChange: (T) -> Unit,
    labelProvider: (T) -> String,
    modifier: Modifier = Modifier
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    var expanded by remember { mutableStateOf(false) }

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

        // ───────── Selector ─────────
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = seleccionado?.let { labelProvider(it) } ?: "",
                onValueChange = {},
                readOnly = true,
                textStyle = theme.input,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = theme.inputBackgroundColor,
                    unfocusedContainerColor = theme.inputBackgroundColor,
                    disabledContainerColor = theme.inputBackgroundColor,
                ),
                shape = theme.cornerRadius
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            AppTextos(
                                text = labelProvider(item),
                                style = theme.bodyText
                            )
                        },
                        onClick = {
                            onSeleccionChange(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
