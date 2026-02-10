package tallerwapo.taller_interfaz.formularios.gastos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Instant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.servicios.FormulariosService
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.campoEntrada.*
import tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones.ValidacionesCampoEntrada
import tallerwapo.taller_interfaz.objetos.scroll.ScrollableContent
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Suppress("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNuevoGasto(
    cita: CitaBO,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val gastosRepo = AppContexto.gastosRepo
    val validaciones = ValidacionesCampoEntrada()

    var descripcion by remember { mutableStateOf("") }
    var importe by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf<Instant>(Clock.System.now()) }
    var observaciones by remember { mutableStateOf("") }

    var mostrarPickerFecha by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val scope = rememberCoroutineScope()

    // ───────── Validación del formulario ─────────
    fun formularioEsValido(): Boolean {
        return descripcion.isNotBlank() &&
                importe.toDoubleOrNull() != null
    }

    fun crearGasto() {
        val cantidad = importe.toDoubleOrNull() ?: return

        scope.launch(Dispatchers.IO) {
            val gasto = GastoBO(
                descripcion = descripcion,
                importe = cantidad,
                fecha = fecha,
                observaciones = observaciones
            )

            val respuesta: RespuestaDTO<GastoBO> =
                gastosRepo.crearGasto(gasto, cita)

            FormulariosService.gestionarRespuestaApi(
                respuesta,
                accionCerrar = { onCerrar() }
            )
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
        ScrollableContent {
            Column(
                modifier = Modifier.padding(theme.paddingM)
            ) {
                Text(
                    text = "Nuevo Gasto",
                    style = theme.title,
                    modifier = Modifier.padding(bottom = theme.paddingL)
                )

                Spacer(Modifier.height(theme.paddingL))

                // ───────── DESCRIPCIÓN (OBLIGATORIA) ─────────
                CampoEntradaRow(
                    titulo = "Descripción",
                    valor = descripcion,
                    onValueChange = { descripcion = it },
                    obligatorio = true
                )

                Spacer(Modifier.height(theme.paddingS))

                // ───────── IMPORTE (OBLIGATORIO + NUMÉRICO) ─────────
                CampoEntradaRow(
                    titulo = "Importe",
                    valor = importe,
                    onValueChange = { importe = it },
                    obligatorio = true,
                    validaciones = listOf(validaciones.validarNumero)
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
        }
    }
}
