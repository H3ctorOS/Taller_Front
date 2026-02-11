package tallerwapo.taller_interfaz.pantallas.calendario

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.ui.Alignment
import kotlin.time.Instant
import tallerwapo.core.contexto.AppContexto.citasRepo
import tallerwapo.core.dominio.dto.calendario.CitaSemanaDTO
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaCitas
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.citas.FormularioNuevaCita
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente

class DiasSemanaScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var semana by remember { mutableStateOf<CitaSemanaDTO?>(null) }

        // Formulario
        var mostrarFormularioNuevaCita by remember { mutableStateOf(false) }
        var fechaInicioPredeterminada by remember { mutableStateOf<Instant?>(null) }

        // Cargar semana
        LaunchedEffect(Unit) {
            semana = citasRepo.citasSemanaActual()
        }

        semana?.let { s ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val dias = listOf(
                    "Lunes" to s.getLunesBO().map { CitaBoUI(it) },
                    "Martes" to s.getMartesBO().map { CitaBoUI(it) },
                    "Miércoles" to s.getMiercolesBO().map { CitaBoUI(it) },
                    "Jueves" to s.getJuevesBO().map { CitaBoUI(it) },
                    "Viernes" to s.getViernesBO().map { CitaBoUI(it) }
                )

                dias.forEach { (nombreDia, citasUI) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Box(
                                modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                        ) {
                        AppTextos(nombreDia, style = theme.title)
                    }

                        Spacer(Modifier.height(4.dp))

                        ListaCitas(
                            listaCitasUI = citasUI,
                            citaSeleccionada = null,
                            onCitaSeleccionada = {},
                            onCitaDoubleClick = {
                                // Abrir formulario nueva cita con fecha del día
                                fechaInicioPredeterminada = it.cita.fechaInicio
                                mostrarFormularioNuevaCita = true
                            },
                            onNewClick = {
                                // Abrir formulario nueva cita con fecha del día de la columna
                                // Tomamos el primer elemento como referencia de fecha, si hay
                                val primeraFecha: Instant? = citasUI.firstOrNull()?.cita?.fechaInicio
                                fechaInicioPredeterminada = primeraFecha
                                mostrarFormularioNuevaCita = true
                            },
                            onNuevoIngresoClick = {},
                            onNuevoGastoClick = {},
                            mostrarNew = true,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }

        // --- Formulario nueva cita ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevaCita,
            onCerrar = { mostrarFormularioNuevaCita = false }
        ) {
            FormularioNuevaCita(
                fechaInicioPredeterminada = fechaInicioPredeterminada,
                onCerrar = { mostrarFormularioNuevaCita = false }
            )
        }
    }
}
