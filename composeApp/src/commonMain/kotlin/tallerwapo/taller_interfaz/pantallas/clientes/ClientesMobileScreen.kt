package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaClientes
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.pantallas.vehiculos.VehiculosMobileScreen

/**
 * Pantalla de clientes simplificada para móviles
 * Solo muestra la lista de clientes
 * Al seleccionar un cliente navega a una nueva pantalla de detalles
 */
object ClientesMobileScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.current
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var listaClientes by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }

        // Función para actualizar la lista de clientes
        suspend fun actualizarListaClientes() {
            try {
                listaClientes = AppContexto.clientesRepo.buscarTodos()
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error desconocido",
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }

        LaunchedEffect(Unit) {
            actualizarListaClientes()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Lista de clientes
            ListaClientes(
                clientes = listaClientes,
                clienteSeleccionado = null,
                onClienteSeleccionado = { cliente ->
                    // Navegar a la pantalla de detalles del cliente
                    navigator?.push(VehiculosMobileScreen(cliente))
                },
                onClienteDoubleClick = {},
                modifier = Modifier.fillMaxWidth().weight(1f),
                onNewClick = {},
                mostrarNew = false
            )
        }
    }
}
