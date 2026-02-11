package tallerwapo.taller_interfaz.pantallas.calendario.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.objetos.scroll.ScrollableContent
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

/**
 * Panel lateral de meses y semanas con scroll vertical
 *
 * @param semanaActual Número de la semana que se está mostrando actualmente (1..52/53)
 * @param onSemanaSeleccionada Callback al seleccionar otra semana
 */
@Composable
fun PanelMesesSemanas(
    semanaActual: Int,
    onSemanaSeleccionada: (Int) -> Unit = {}
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    val meses = listOf(
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    // Contador de semana global (aproximadamente 4 semanas por mes)
    var semanaNumero = 1

    Box(
        modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .padding(4.dp)
    ) {
        ScrollableContent {
            meses.forEach { mes ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                ) {
                    // ─── Nombre del mes centrado ───
                    AppTextos(
                        text = mes,
                        style = theme.subTitleText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(4.dp))

                    // ─── Semanas del mes ───
                    repeat(4) { _ ->
                        val actualSemana = semanaNumero
                        val isSemanaActual = actualSemana == semanaActual

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .height(24.dp)
                                .background(
                                    color = if (isSemanaActual) theme.selectedBackgroundColor else theme.surfaceColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onSemanaSeleccionada(actualSemana) },
                            contentAlignment = Alignment.Center
                        ) {
                            AppTextos(
                                text = actualSemana.toString(),
                                style = theme.bodyText,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        semanaNumero++
                    }
                }
            }
        }
    }
}
