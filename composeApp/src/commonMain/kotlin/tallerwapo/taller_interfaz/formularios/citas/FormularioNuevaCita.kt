package tallerwapo.taller_interfaz.formularios.citas

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
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.utils.FormulariosService
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.campoEntrada.*
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNuevaCita(
    vehiculo: VehiculoBO? = null,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val citasRepo = ApiContexto.citasRepo
    val vehiculosRepo = ApiContexto.vehiculosRepo

    var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }

    var concepto by remember { mutableStateOf("") }
    var vehiculoSelccionado by remember { mutableStateOf<VehiculoBO?>(vehiculo) }
    var fechaInicio by remember { mutableStateOf<Instant>(Clock.System.now()) }
    var fechaFin by remember { mutableStateOf<Instant>(Clock.System.now()+2.days) }
    var observaciones by remember { mutableStateOf("") }

    var mostrarPickerInicio by remember { mutableStateOf(false) }
    var mostrarPickerFin by remember { mutableStateOf(false) }

    // Estados de los calendarios
    val datePickerStateInicio = rememberDatePickerState()
    val datePickerStateFin = rememberDatePickerState()

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (vehiculoSelccionado == null) {
            vehiculosRepo.buscarTodos()?.let { listaVehiculos = it }
        }
    }

    fun formularioEsValido(): Boolean {
        return vehiculoSelccionado != null && concepto.isNotBlank()
    }

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
            //llamar al repo
            val respuesta = citasRepo.crearCita(cita)

            //Gestionar la respuesta
            FormulariosService.gestionarRespuestaApi(respuesta) { onCerrar() }
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
                onValueChange = { concepto = it }
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

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        )
    }
}
