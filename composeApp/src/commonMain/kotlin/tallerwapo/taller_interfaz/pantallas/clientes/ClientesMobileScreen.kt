package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.clientes.FormularioNuevoCliente
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaClientes
import tallerwapo.taller_interfaz.pantallas.principal.componentesMovil.BottomBarMobile
import tallerwapo.taller_interfaz.pantallas.vehiculos.VehiculosMobileScreen
import tallerwapo.taller_interfaz.themes.AppThemeProvider

object ClientesMobileScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var listaClientes by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }
        var mostrarFormularioNuevoCliente by remember { mutableStateOf(false) }

        // 🔹 Actualizar lista clientes
        suspend fun actualizarListaClientes() {
            try {
                listaClientes = AppContexto.clientesRepo.buscarTodos()
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error desconocido",
                    botones = listOf(
                        MensajesEmergentes.BotonDialogo("Ok") {}
                    )
                )
            }
        }

        LaunchedEffect(Unit) {
            actualizarListaClientes()
        }

        Scaffold(
            bottomBar = {
                BottomBarMobile()
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                ListaClientes(
                    clientes = listaClientes,
                    clienteSeleccionado = null,
                    onClienteSeleccionado = { cliente ->
                        // 🔹 Navegación interna → push correcto
                        navigator?.push(VehiculosMobileScreen(cliente))
                    },
                    onClienteDoubleClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onNewClick = { mostrarFormularioNuevoCliente = true },
                    mostrarNew = true
                )
            }

            // 🔹 Formulario nuevo cliente
            FormularioEmergente(
                mostrar = mostrarFormularioNuevoCliente,
                onCerrar = { mostrarFormularioNuevoCliente = false }
            ) {
                FormularioNuevoCliente {
                    mostrarFormularioNuevoCliente = false
                    scope.launch { actualizarListaClientes() }
                }
            }
        }
    }
}