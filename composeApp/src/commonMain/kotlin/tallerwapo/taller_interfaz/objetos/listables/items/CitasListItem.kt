package tallerwapo.taller_interfaz.objetos.listables.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.servicios.formatoDiaMesAnio
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

data class CitasListItem(
    override val bo: CitaBO
) : ListableBO<CitaBO> {

    override val titulo: String = bo.concepto

    override val subtitulo: String =
        "${bo.fechaInicio.formatoDiaMesAnio()}      -->     ${bo.fechaFinalizada.formatoDiaMesAnio()}"

    override val descripcion: String? = bo.observaciones

    /**
     * Contenido desplegable que se mostrará al expandir el card
     */
    @Composable
    override fun ContenidoDesplegable() {
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(theme.paddingS)
        ) {

            // Información extendida
            Text(text = "Observaciones: ${bo.observaciones.ifEmpty { "Ninguna" }}")
            Text(text = "Estado: ${bo.codigoEstado.ifEmpty { "Desconocido" }}")
            Text(text = "Inicio: ${bo.fechaInicio.formatoDiaMesAnio()}")
            Text(text = "Fin: ${bo.fechaFinalizada.formatoDiaMesAnio()}")

            Spacer(modifier = Modifier.height(theme.paddingS))

            // Ejemplo de botón de acción
            Button(onClick = { /* Acción de ejemplo */ }) {
                Text("Marcar como finalizada")
            }
        }
    }
}
