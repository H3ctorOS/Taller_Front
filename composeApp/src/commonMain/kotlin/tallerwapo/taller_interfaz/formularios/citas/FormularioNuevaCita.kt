package tallerwapo.taller_interfaz.formularios.citas

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.campoEntrada.CampoEntradaRow
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.objetos.campoEntrada.CampoFechaHoraRow
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNuevaCita(
    vehiculo: VehiculoBO?,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val citasRepo = ApiContexto.citasRepo

    // Estados de los campos
    var concepto by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf(LocalDateTime.now()) }
    var fechaFin by remember { mutableStateOf(LocalDateTime.now().plusHours(48)) }
    var observaciones by remember { mutableStateOf("") }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    // Estados para los date pickers
    var mostrarPickerInicio by remember { mutableStateOf(false) }
    var mostrarPickerFin by remember { mutableStateOf(false) }

    val datePickerStateInicio: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = fechaInicio
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    )

    val datePickerStateFin: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = fechaFin
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    )

    // Scroll state para la columna
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .widthIn(max = 800.dp)
            .heightIn(max = 900.dp)
            .background(theme.surfaceColor, theme.cornerRadius)
            .padding(theme.paddingS)
            .fillMaxSize()
    ) {
        // Columna scrollable
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(theme.paddingM)
        ) {
            Text(
                text = "Nueva Cita",
                style = theme.title,
                modifier = Modifier.padding(bottom = theme.paddingL)
            )
            Spacer(Modifier.height(theme.paddingL))

            CampoEntradaRow(titulo = "concepto", valor = concepto, onValueChange = { concepto = it })
            CampoEntradaRow(titulo = "observaciones", valor = observaciones, onValueChange = { observaciones = it })

            CampoFechaHoraRow(
                titulo = "Fecha inicio",
                fecha = fechaInicio,
                onFechaChange = { fechaInicio = it },
                mostrarDatePicker = mostrarPickerInicio,
                onMostrarDatePickerChange = { mostrarPickerInicio = it },
                datePickerState = datePickerStateInicio
            )

            CampoFechaHoraRow(
                titulo = "Fecha fin",
                fecha = fechaFin,
                onFechaChange = { fechaFin = it },
                mostrarDatePicker = mostrarPickerFin,
                onMostrarDatePickerChange = { mostrarPickerFin = it },
                datePickerState = datePickerStateFin
            )

            Spacer(modifier = Modifier.height(theme.paddingL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AppBoton(text = "Cancelar", onClick = { onCerrar() })
                Spacer(modifier = Modifier.width(theme.paddingM))
                AppBoton(
                    text = "Guardar",
                    onClick = { }
                )
            }
        }

        // Barra de scroll
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        )
    }
}