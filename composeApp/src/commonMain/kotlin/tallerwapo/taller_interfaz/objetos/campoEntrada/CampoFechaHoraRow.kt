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
import kotlin.time.Instant
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFechaHoraRow(
    titulo: String,
    fecha: Instant,
    onFechaChange: (Instant) -> Unit,
    mostrarDatePicker: Boolean,
    onMostrarDatePickerChange: (Boolean) -> Unit,
    datePickerState: DatePickerState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    mantenerHoraExistente: Boolean = true,
    zoneId: ZoneId = ZoneId.systemDefault()
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    // Convertimos kotlin.time.Instant → java.time.LocalDateTime para UI
    val localFecha = fecha.toJavaInstant().atZone(zoneId).toLocalDateTime()

    // 🔹 Sincronizar DatePicker con la fecha recibida
    LaunchedEffect(fecha, zoneId) {
        val millis = fecha.toJavaInstant()
            .atZone(zoneId)
            .toLocalDate()
            .atStartOfDay(zoneId)
            .toInstant()
            .toEpochMilli()

        datePickerState.selectedDateMillis = millis
        datePickerState.displayedMonthMillis = millis
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = theme.paddingS)
    ) {
        AppTextos(text = titulo, style = theme.bodyText)
        Spacer(Modifier.height(theme.paddingS))

        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = localFecha.format(formatter),
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                textStyle = theme.input,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = theme.inputBackgroundColor,
                    unfocusedContainerColor = theme.inputBackgroundColor,
                    disabledContainerColor = theme.inputBackgroundColor,
                ),
                shape = theme.cornerRadius
            )

            if (enabled) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { onMostrarDatePickerChange(true) }
                )
            }
        }
    }

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onMostrarDatePickerChange(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val nuevaFechaLocal = java.time.Instant
                                .ofEpochMilli(millis)
                                .atZone(zoneId)
                                .toLocalDate()

                            val fechaFinal = if (mantenerHoraExistente) {
                                val horaActual = localFecha.toLocalTime()
                                LocalDateTime.of(nuevaFechaLocal, horaActual)
                            } else {
                                LocalDateTime.of(nuevaFechaLocal, LocalTime.MIDNIGHT)
                            }

                            // Convertimos de vuelta a kotlin.time.Instant
                            val nuevoInstant = fechaFinal
                                .atZone(zoneId)
                                .toInstant()
                                .toKotlinInstant()

                            onFechaChange(nuevoInstant)
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
