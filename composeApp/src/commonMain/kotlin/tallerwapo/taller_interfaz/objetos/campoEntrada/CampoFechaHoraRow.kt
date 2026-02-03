package tallerwapo.taller_interfaz.objetos.campoEntrada

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import java.time.*
import java.time.format.DateTimeFormatter

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFechaHoraRow(
    titulo: String,
    fecha: LocalDateTime,
    onFechaChange: (LocalDateTime) -> Unit,
    mostrarDatePicker: Boolean,
    onMostrarDatePickerChange: (Boolean) -> Unit,
    datePickerState: DatePickerState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    mantenerHoraExistente: Boolean = true
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = theme.paddingS)
    ) {
        AppTextos(text = titulo, style = theme.bodyText)

        Spacer(Modifier.height(theme.paddingS))

        // Cambio clave: envolvemos TextField en Box y ponemos clickable en el Box
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = fecha.format(formatter),
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = theme.input,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = theme.inputBackgroundColor,
                    unfocusedContainerColor = theme.inputBackgroundColor,
                    disabledContainerColor = theme.inputBackgroundColor,
                ),
                shape = theme.cornerRadius
            )

            // Clickable en el contenedor → evita conflictos con TextField
            if (enabled) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { onMostrarDatePickerChange(true) }
                )
            }
        }
    }

    // El diálogo se mantiene igual (ya está fuera de la Column → sibling)
    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onMostrarDatePickerChange(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val nuevaFecha = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()

                            val fechaFinal = if (mantenerHoraExistente) {
                                LocalDateTime.of(nuevaFecha, fecha.toLocalTime())
                            } else {
                                LocalDateTime.of(nuevaFecha, LocalTime.MIDNIGHT)
                            }

                            onFechaChange(fechaFinal)
                        }
                        onMostrarDatePickerChange(false)
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { onMostrarDatePickerChange(false) }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}