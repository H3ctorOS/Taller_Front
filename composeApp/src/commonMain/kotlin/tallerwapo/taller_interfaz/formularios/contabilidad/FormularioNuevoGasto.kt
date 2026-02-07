package tallerwapo.taller_interfaz.formularios.gastos

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Instant
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.servicios.FormulariosService
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.campoEntrada.*
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNuevoGasto(
    cita: CitaBO? = null,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val gastosRepo = ApiContexto.gastosRepo

    var descripcion by remember { mutableStateOf("") }
    var importe by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf<Instant>(Clock.System.now()) }
    var observaciones by remember { mutableStateOf("") }

    var mostrarPickerFecha by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    fun formularioEsValido() = descripcion.isNotBlank() && importe.toDoubleOrNull() != null

    fun crearGasto() {
        val cantidad = importe.toDoubleOrNull() ?: return

        scope.launch(Dispatchers.IO) {
            val gasto = GastoBO(
                descripcion = descripcion,
                importe = cantidad,
                fecha = fecha,
                observaciones = observaciones
            )

            val respuesta: RespuestaDTO<GastoBO> = if (cita != null) {
                gastosRepo.crearGasto(gasto, cita)
            } else {
                gastosRepo.crearGasto(gasto)
            }

            FormulariosService.gestionarRespuestaApi(respuesta) { onCerrar() }
        }
    }

    Box(
        modifier = Modifier
            .widthIn(max = 600.dp)
            .heightIn(max = 600.dp)
            .background(theme.surfaceColor, theme.cornerRadius)
            .padding(theme.paddingS)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(theme.paddingM)
        ) {
            Text(
                text = "Nuevo Gasto",
                style = theme.title,
                modifier = Modifier.padding(bottom = theme.paddingL)
            )

            Spacer(Modifier.height(theme.paddingL))

            CampoEntradaRow(
                titulo = "Descripción",
                valor = descripcion,
                onValueChange = { descripcion = it }
            )

            Spacer(Modifier.height(theme.paddingS))

            CampoEntradaRow(
                titulo = "Importe",
                valor = importe,
                onValueChange = { importe = it }
            )

            Spacer(Modifier.height(theme.paddingS))

            CampoFechaHoraRow(
                titulo = "Fecha",
                fecha = fecha,
                onFechaChange = { fecha = it },
                mostrarDatePicker = mostrarPickerFecha,
                onMostrarDatePickerChange = { mostrarPickerFecha = it },
                datePickerState = datePickerState
            )

            Spacer(Modifier.height(theme.paddingL))

            CampoEntradaTextoRow(
                titulo = "Observaciones",
                valor = observaciones,
                onValueChange = { observaciones = it }
            )

            Spacer(Modifier.height(theme.paddingL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AppBoton(text = "Cancelar", onClick = onCerrar)
                Spacer(modifier = Modifier.width(theme.paddingM))
                AppBoton(
                    text = "Guardar",
                    enabled = formularioEsValido(),
                    onClick = { crearGasto() }
                )
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        )
    }
}
