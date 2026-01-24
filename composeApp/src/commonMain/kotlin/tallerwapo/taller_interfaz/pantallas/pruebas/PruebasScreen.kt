package tallerwapo.taller_interfaz.pantallas.pruebas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.taller_interfaz.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.pantallas.formularios.FormularioNuevoCliente
import tallerwapo.taller_interfaz.pantallas.formularios.FormularioNuevoVehiculo
import tallerwapo.taller_interfaz.themes.AppTheme

class PruebasScreen : Screen {

    @Composable
    override fun Content() {

        // Estados para mostrar los emergentes
        var mostrarCliente by remember { mutableStateOf(false) }
        var mostrarNuevoVehiculo by remember { mutableStateOf(false) }
        var mostrarConfirmacion by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(AppTheme.PaddingM),
            verticalArrangement = Arrangement.spacedBy(AppTheme.PaddingM)
        ) {

            Text(
                text = "Pantalla de pruebas",
                style = AppTheme.Title
            )

            // Botón para abrir el formulario de cliente
            Button(onClick = { mostrarCliente = true }) {
                Text("Nuevo Cliente")
            }

            // Botón para abrir el formulario de Vehiculos
            Button(onClick = { mostrarNuevoVehiculo = true }) {
                Text("Nuevo Vehiculo")
            }

            // Botón para abrir el diálogo de confirmación
            Button(onClick = { mostrarConfirmacion = true }) {
                Text("Mostrar Confirmación")
            }
        }

        // Formulario emergente para nuevo cliente
        FormularioEmergente(
            mostrar = mostrarCliente,
            onCerrar = { mostrarCliente = false }
        ) {
            FormularioNuevoCliente(
                onCerrar = { mostrarCliente = false } // cerrar al pulsar guardar o cancelar
            )
        }

        // Formulario emergente para nuevo Vehiculo
        FormularioEmergente(
            mostrar = mostrarNuevoVehiculo,
            onCerrar = { mostrarNuevoVehiculo = false }
        ) {
            FormularioNuevoVehiculo(
                onCerrar = { mostrarNuevoVehiculo = false },
                cliente = null
            )
        }



        // Diálogo de confirmación
        if (mostrarConfirmacion) {
            MensajesEmergentes.mostrarDialogo(
                titulo = "Confirmación",
                mensaje = "¿Deseas guardar los cambios?",
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("Sí") { mostrarConfirmacion = false },
                    MensajesEmergentes.BotonDialogo("No") { mostrarConfirmacion = false }
                )
            )
        }
    }
}