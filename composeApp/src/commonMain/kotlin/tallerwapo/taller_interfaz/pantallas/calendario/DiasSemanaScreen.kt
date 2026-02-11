package tallerwapo.taller_interfaz.pantallas.calendario

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
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
        var semanaActual by remember { mutableStateOf(0) }
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
            // ─── Panel izquierdo con meses y semanas ───
            PanelMesesSemanas(
                semanaActual = semanaActual,
                onSemanaSeleccionada = { semanaSeleccionada ->
                    semanaActual = semanaSeleccionada
                    scope.launch {
                        semanaDTO = citasRepo.citasSemana(semanaSeleccionada)
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            // ─── Panel derecho con días de la semana ───
            semanaDTO?.let { s ->
                val diasConFecha = listOf(
                    Triple("Lunes", s.fechaLunes, s.getLunesBO().map { CitaBoUI(it) }),
                    Triple("Martes", s.fechaMartes, s.getMartesBO().map { CitaBoUI(it) }),
                    Triple("Miércoles", s.fechaMiercoles, s.getMiercolesBO().map { CitaBoUI(it) }),
                    Triple("Jueves", s.fechaJueves, s.getJuevesBO().map { CitaBoUI(it) }),
                    Triple("Viernes", s.fechaViernes, s.getViernesBO().map { CitaBoUI(it) })
                )

                val dayFormat = SimpleDateFormat("d", Locale.getDefault())
                val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())

                // ─── Agrupar días por mes para mostrar el nombre del mes arriba ───
                val diasAgrupadosPorMes = diasConFecha.groupBy { (_, fecha, _) ->
                    monthFormat.format(Date(fecha))
                }

                // ─── Calculamos todos los días en orden para repartir equitativamente ───
                val diasOrdenados = diasAgrupadosPorMes.flatMap { it.value }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // ─── Fila de nombres de meses ───
                    Row(modifier = Modifier.fillMaxWidth()) {
                        diasAgrupadosPorMes.forEach { (mes, diasMes) ->
                            // Cada mes ocupa el ancho proporcional al número de días que tiene
                            Row(
                                modifier = Modifier.weight(diasMes.size.toFloat())
                            ) {
                                AppTextos(
                                    text = mes,
                                    style = theme.subTitleText,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // ─── Fila de días y citas ───
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        diasOrdenados.forEach { (nombreDia, fecha, citasUI) ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {
                                val diaNumero = dayFormat.format(Date(fecha))
                                AppTextos(
                                    text = "$nombreDia $diaNumero",
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
            }
        }

        // ─── Formulario nueva cita ───
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
