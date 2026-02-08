package tallerwapo.taller_interfaz.objetos.listables.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.servicios.formatoDiaMesAnio
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

data class CitasListItem(
    override val bo: CitaBoUI, // <-- Usamos la UI wrapper
    val onNuevoIngresoClick: (CitaBoUI) -> Unit,
    val onNuevoGastoClick: (CitaBoUI) -> Unit
) : ListableBO<CitaBoUI> {

    override val titulo: String = bo.cita.concepto
    override val subtitulo: String =
        "${bo.cita.fechaInicio.formatoDiaMesAnio()} → ${bo.cita.fechaFinalizada.formatoDiaMesAnio()}"
    override val descripcion: String? = bo.cita.observaciones

    @Composable
    override fun ContenidoDesplegable() {
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(theme.paddingS)
        ) {
            // Información extendida de la cita
            Text(text = "Observaciones: ${bo.cita.observaciones.ifEmpty { "Ninguna" }}")
            Text(text = "Estado: ${bo.cita.codigoEstado.ifEmpty { "Desconocido" }}")
            Text(text = "Inicio: ${bo.cita.fechaInicio.formatoDiaMesAnio()}")
            Text(text = "Fin: ${bo.cita.fechaFinalizada.formatoDiaMesAnio()}")

            Spacer(modifier = Modifier.height(16.dp))

            // --- Sección Ingresos ---
            Text(text = "Ingresos", style = theme.subTitleText)
            Spacer(modifier = Modifier.height(8.dp))
            bo.ingresos.forEach { ingreso: IngresoBO ->
                Text(
                    text = "${ingreso.concepto} - ${ingreso.importe}€ - ${ingreso.fecha.formatoDiaMesAnio()}",
                    style = theme.bodyText
                )
            }
            Button(
                onClick = { onNuevoIngresoClick(bo) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("+ Ingreso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Sección Gastos ---
            Text(text = "Gastos", style = theme.subTitleText)
            Spacer(modifier = Modifier.height(8.dp))
            bo.gastos.forEach { gasto: GastoBO ->
                Text(
                    text = "${gasto.descripcion} - ${gasto.importe}€ - ${gasto.fecha.formatoDiaMesAnio()}",
                    style = theme.bodyText
                )
            }
            Button(
                onClick = { onNuevoGastoClick(bo) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("+ Gasto")
            }
        }
    }
}
