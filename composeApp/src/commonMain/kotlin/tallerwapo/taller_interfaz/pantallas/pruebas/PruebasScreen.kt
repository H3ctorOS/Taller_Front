package tallerwapo.taller_interfaz.pantallas.pruebas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.ListableBOList
import tallerwapo.taller_interfaz.objetos.listables.items.VehiculoListItem
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.formularios.FormularioNuevoCliente
import tallerwapo.taller_interfaz.formularios.FormularioNuevoVehiculo
import tallerwapo.taller_interfaz.themes.AppTheme

class PruebasScreen : Screen {

    @Composable
    override fun Content() {

        var mostrarCliente by remember { mutableStateOf(false) }
        var mostrarNuevoVehiculo by remember { mutableStateOf(false) }
        var mostrarConfirmacion by remember { mutableStateOf(false) }

        // 🔹 DATOS DE PRUEBA
        val vehiculos = remember {
            listOf(
                VehiculoBO(1, 1, "Toyota", "Corolla"),
                VehiculoBO(2, 2, "Singapore", "Corolla"),
                VehiculoBO(3, 3, "Singapore", "Corolla"),
                VehiculoBO(4, 4, "Singapore", "Corolla"),

            )
        }

        val vehiculoItems = remember {
            vehiculos.map { VehiculoListItem(it) }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.PaddingM)
        ) {

            AppTextos(
                text = "Pantalla de pruebas",
                style = AppTheme.Title
            )

            AppBoton(
                text = "Nuevo Cliente",
                onClick = { mostrarCliente = true }
            )

            AppBoton(
                text = "Nuevo Vehiculo",
                onClick = { mostrarNuevoVehiculo = true }
            )

            AppBoton(
                text = "Mostrar Confirmación",
                onClick = { mostrarConfirmacion = true }
            )

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            ) {
                ListableBOList(
                    items = vehiculoItems,
                    onItemClick = { vehiculo ->
                        println("Click en vehículo: ${vehiculo.matricula}")
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // ---- EMERGENTES ----

        FormularioEmergente(
            mostrar = mostrarCliente,
            onCerrar = { mostrarCliente = false }
        ) {
            FormularioNuevoCliente(
                onCerrar = { mostrarCliente = false }
            )
        }

        FormularioEmergente(
            mostrar = mostrarNuevoVehiculo,
            onCerrar = { mostrarNuevoVehiculo = false }
        ) {
            FormularioNuevoVehiculo(
                onCerrar = { mostrarNuevoVehiculo = false }
            )
        }

        if (mostrarConfirmacion) {
            MensajesEmergentes.mostrarDialogo(
                titulo = "Confirmación",
                mensaje = "¿Deseas guardar los cambios?",
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("Sí") {
                        mostrarConfirmacion = false
                    },
                    MensajesEmergentes.BotonDialogo("No") {
                        mostrarConfirmacion = false
                    }
                )
            )
        }
    }
}
