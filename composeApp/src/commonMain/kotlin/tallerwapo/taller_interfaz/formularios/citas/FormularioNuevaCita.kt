package tallerwapo.taller_interfaz.formularios.citas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.setSelectedDate
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.servicios.FormulariosService
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.campoEntrada.*
import tallerwapo.taller_interfaz.objetos.scroll.ScrollableContent
import tallerwapo.taller_interfaz.themes.AppThemeProvider


@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNuevaCita(
    vehiculo: VehiculoBO? = null,
    fechaInicioPredeterminada: Instant? = null, // <-- nuevo parámetro opcional
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val citasRepo = AppContexto.citasRepo
    val vehiculosRepo = AppContexto.vehiculosRepo

    var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }
    var concepto by remember { mutableStateOf("") }
    var vehiculoSelccionado by remember { mutableStateOf<VehiculoBO?>(vehiculo) }

    // Usar la fecha predeterminada si se pasa, sino la actual
    var fechaInicio by remember { mutableStateOf(fechaInicioPredeterminada ?: Clock.System.now()) }
    var fechaFin by remember { mutableStateOf(fechaInicio + 2.days) }

    var observaciones by remember { mutableStateOf("") }
    var mostrarPickerInicio by remember { mutableStateOf(false) }
    var mostrarPickerFin by remember { mutableStateOf(false) }

    // DatePickerStates con fecha inicial
    val datePickerStateInicio = rememberDatePickerState(
        initialSelectedDateMillis = fechaInicio.toEpochMilliseconds()
    )
    val datePickerStateFin = rememberDatePickerState(
        initialSelectedDateMillis = fechaFin.toEpochMilliseconds()
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (vehiculoSelccionado == null) {
            vehiculosRepo.buscarTodos()?.let { listaVehiculos = it }
        }
    }

    fun formularioEsValido() = vehiculoSelccionado != null && concepto.isNotBlank()

    fun crearCita() {
        val vehiculoActual = vehiculoSelccionado ?: return
        scope.launch(Dispatchers.IO) {
            val cita = CitaBO(
                vehiculoUuid = vehiculoActual.uuid,
                concepto = concepto,
                fechaInicio = fechaInicio,
                fechaFinalizada = fechaFin,
                observaciones = observaciones
            )
            val respuesta = citasRepo.crearCita(cita)
            FormulariosService.gestionarRespuestaApi(
                respuesta,
                accionCerrar = { onCerrar() }
            )
        }
    }

    Box(
        modifier = Modifier
            .widthIn(max = 800.dp)
            .heightIn(max = 750.dp)
            .background(theme.surfaceColor, theme.cornerRadius)
            .padding(theme.paddingS)
            .fillMaxSize()
    ) {
        ScrollableContent {
            Column(
                modifier = Modifier.padding(theme.paddingM)
            ) {
                Text(
                    text = "Nueva Cita",
                    style = theme.title,
                    modifier = Modifier.padding(bottom = theme.paddingL)
                )

                Spacer(Modifier.height(theme.paddingL))

                if (vehiculo == null) {
                    SeleccionableRow(
                        titulo = "Vehículo",
                        items = listaVehiculos,
                        seleccionado = vehiculoSelccionado,
                        onSeleccionChange = { vehiculoSelccionado = it },
                        labelProvider = { it.matricula }
                    )
                } else {
                    CampoEntradaRow(
                        titulo = "Vehículo",
                        valor = vehiculoSelccionado!!.matricula,
                        onValueChange = {},
                        enabled = false
                    )
                }

                Spacer(Modifier.height(theme.paddingS))

                CampoEntradaRow(
                    titulo = "Concepto",
                    valor = concepto,
                    onValueChange = { concepto = it },
                    obligatorio = true
                )

                Spacer(Modifier.height(theme.paddingS))

                CampoFechaHoraRow(
                    titulo = "Fecha inicio",
                    fecha = fechaInicio,
                    onFechaChange = { fechaInicio = it },
                    mostrarDatePicker = mostrarPickerInicio,
                    onMostrarDatePickerChange = { mostrarPickerInicio = it },
                    datePickerState = datePickerStateInicio
                )

                Spacer(Modifier.height(theme.paddingS))

                CampoFechaHoraRow(
                    titulo = "Fecha fin",
                    fecha = fechaFin,
                    onFechaChange = { fechaFin = it },
                    mostrarDatePicker = mostrarPickerFin,
                    onMostrarDatePickerChange = { mostrarPickerFin = it },
                    datePickerState = datePickerStateFin
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
                        onClick = { crearCita() }
                    )
                }
            }
        }
    }
}
