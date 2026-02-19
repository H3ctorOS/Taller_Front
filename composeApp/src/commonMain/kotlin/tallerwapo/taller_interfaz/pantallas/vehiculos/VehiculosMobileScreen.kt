package tallerwapo.taller_interfaz.pantallas.vehiculos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaVehiculos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

/**
 * Pantalla de vehículos para móviles
 * Muestra todos los vehículos de un cliente
 */
class VehiculosMobileScreen(private val cliente: ClienteBO) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }

        // Función para actualizar la lista de vehículos del cliente
        suspend fun actualizarListaVehiculos() {
            try {
                listaVehiculos = AppContexto.vehiculosRepo.buscarPorCliente(cliente)
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error al cargar vehículos",
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }

        LaunchedEffect(Unit) {
            actualizarListaVehiculos()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Vehículos de ${cliente.nombre}") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                ListaVehiculos(
                    vehiculos = listaVehiculos,
                    vehiculoSeleccionado = null,
                    onVehiculoSeleccionado = { /* opcional */ },
                    onVehiculoDoubleClick = { /* opcional */ },
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    mostrarNew = false,
                    onNewClick = {}
                )
            }
        }
    }
}
