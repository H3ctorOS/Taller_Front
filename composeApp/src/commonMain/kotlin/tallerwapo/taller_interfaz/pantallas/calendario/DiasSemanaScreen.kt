package tallerwapo.taller_interfaz.pantallas.calendario

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto.citasRepo
import tallerwapo.core.dominio.dto.calendario.CitaSemanaDTO
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaCitas
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.citas.FormularioNuevaCita
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.pantallas.calendario.componentes.PanelMesesSemanas

class DiasSemanaScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var semanaDTO by remember { mutableStateOf<CitaSemanaDTO?>(null) }
        var semanaActual by remember { mutableStateOf(0) } // Semana resaltada
        var mostrarFormularioNuevaCita by remember { mutableStateOf(false) }

        // Cargar semana actual
        LaunchedEffect(Unit) {
            semanaDTO = citasRepo.citasSemanaActual()
            semanaDTO?.let { s ->
                semanaActual = s.numeroSemana
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Panel izquierdo con meses y semanas
            PanelMesesSemanas(
                semanaActual = semanaActual,
                onSemanaSeleccionada = { semanaSeleccionada ->
                    semanaActual = semanaSeleccionada

                    // Cargar la semana seleccionada simulada
                    scope.launch {
                        semanaDTO = citasRepo.citasSemana(semanaSeleccionada)
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            // Panel derecho con días de la semana
            semanaDTO?.let { s ->
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
                        AppTextos(
                            text = nombreDia,
                            style = theme.title,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(Modifier.height(4.dp))

                        ListaCitas(
                            listaCitasUI = citasUI,
                            citaSeleccionada = null,
                            onCitaSeleccionada = {},
                            onCitaDoubleClick = null,
                            onNewClick = { mostrarFormularioNuevaCita = true },
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
                onCerrar = { mostrarFormularioNuevaCita = false }
            )
        }
    }
}
