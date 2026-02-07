package tallerwapo.taller_interfaz.formularios.ingresos

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
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.servicios.FormulariosService
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.campoEntrada.*
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import kotlin.time.Clock
import kotlin.time.Instant

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNuevoIngreso(
    cita: CitaBO,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val ingresosRepo = ApiContexto.ingresosRepo

    var concepto by remember { mutableStateOf("") }
    var importe by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf<Instant>(Clock.System.now()) }
    var observaciones by remember { mutableStateOf("") }

    var mostrarPickerFecha by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    fun formularioEsValido() = concepto.isNotBlank() && importe.toDoubleOrNull() != null

    fun crearIngreso() {
        val cantidad = importe.toDoubleOrNull() ?: return
        scope.launch(Dispatchers.IO) {
            // Creamos el BO sin referencia a la cita
            val ingreso = IngresoBO(
                concepto = concepto,
                importe = cantidad,
                fecha = fecha,
                codEstado = "ACTIVO",
                observaciones = observaciones
            )

            // Llamamos al repo pasando también la cita asociada si es necesario
            var respuesta: RespuestaDTO<IngresoBO>

            if (cita != null) {
                respuesta = ingresosRepo.crearIngreso(ingreso, cita)
            }else{
                respuesta = ingresosRepo.crearIngreso(ingreso)
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
                text = "Nuevo Ingreso",
                style = theme.title,
                modifier = Modifier.padding(bottom = theme.paddingL)
            )

            Spacer(Modifier.height(theme.paddingL))

            CampoEntradaRow(
                titulo = "Concepto",
                valor = concepto,
                onValueChange = { concepto = it }
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
                    onClick = { crearIngreso() }
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
