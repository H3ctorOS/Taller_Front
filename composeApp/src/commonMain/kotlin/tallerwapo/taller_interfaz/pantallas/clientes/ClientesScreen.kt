package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.FormularioNuevoCliente
import tallerwapo.taller_interfaz.formularios.FormularioNuevoVehiculo
import tallerwapo.taller_interfaz.formularios.FormulatioModificarCliente
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.pantallas.clientes.componentes.ClientesPanelinfo
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaClientes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaVehiculos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

class ClientesScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()

        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        // --- Estado de la pantalla ---
        var mostrarFormularioEditarCliente by remember { mutableStateOf(false) }
        var mostrarFormularioNueloCliente by remember { mutableStateOf(false) }
        var mostrarFormularioNuevoVehiculo by remember { mutableStateOf(false) }
        var clienteSeleccionado by remember { mutableStateOf<ClienteBO?>(null) }
        var listaClientes by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }

        var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }


        var vehiculoSeleccionado by remember { mutableStateOf<VehiculoBO?>(null) }


        // --- Función para actualizar la lista de clientes ---
        suspend fun actualizarListaClientes() {
            try {
                val recibida = ApiContexto.clientesRepo.buscarTodosLosClientes()
                if (recibida != null) listaClientes = recibida

            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error desconocido",
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }

        // --- Función para actualizar la lista de clientes ---
        suspend fun actualizarListaVehiculos(cliente: ClienteBO) {
            try {
                val recibida = ApiContexto.vehiculosRepo.buscarPorCliente(cliente)
                if (recibida != null) listaVehiculos = recibida

            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: ("Ha habido algun problema a buscar la lista de vehiculos del cliente: " + cliente.nombre),
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }




        // --- Cargar lista al iniciar ---
        LaunchedEffect(Unit) {
            actualizarListaClientes()
        }

        Row(modifier = Modifier.fillMaxSize()) {

            // --- Lista de clientes a la izquierda ---
            ListaClientes(
                clientes = listaClientes,
                clienteSeleccionado = clienteSeleccionado,
                onClienteSeleccionado = {
                    clienteSeleccionado = it
                    scope.launch {
                        actualizarListaVehiculos(it)
                    }
                },
                onClienteDoubleClick = { cliente ->
                    clienteSeleccionado = cliente
                    mostrarFormularioEditarCliente = true
                },
                modifier = Modifier.width(300.dp).fillMaxHeight(),
                onNewClick = {
                    mostrarFormularioNueloCliente = true
                },
                mostrarNew = true

            )

            // --- Columna derecha ---
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(theme.paddingM)
            ) {

                // Lista de coches del cliente seleccionado
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        ListaVehiculos(
                            vehiculos = listaVehiculos,
                            vehiculoSeleccionado = vehiculoSeleccionado,
                            onVehiculoSeleccionado = { vehiculoSeleccionado = it },
                            onVehiculoDoubleClick = { vehiculo ->
                                vehiculoSeleccionado = vehiculo
                             },
                            modifier = Modifier.width(300.dp).fillMaxHeight(),
                            mostrarNew = true,
                            onNewClick = {mostrarFormularioNuevoVehiculo = true}

                        )

                    }

                }

                Spacer(modifier = Modifier.weight(1f))

                // Panel info cliente abajo
                ClientesPanelinfo(
                    cliente = clienteSeleccionado,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
            }
        }

        // --- Formulario emergente para editar cliente ---
        FormularioEmergente(
            mostrar = mostrarFormularioEditarCliente,
            onCerrar = {
                mostrarFormularioEditarCliente = false
            }
        ) {
            clienteSeleccionado?.let { cliente ->
                FormulatioModificarCliente(
                    cliente,
                    onCerrar = {
                        mostrarFormularioEditarCliente = false

                        // 🔹 Actualizar lista usando el scope
                        scope.launch {
                            val idSeleccionado = clienteSeleccionado?.uuid
                            actualizarListaClientes()
                            clienteSeleccionado = listaClientes.find { it.uuid == idSeleccionado }
                        }
                    }
                )
            }
        }

        // --- Formulario emergente nuevo cliente ---
        FormularioEmergente(
            mostrar = mostrarFormularioNueloCliente,
            onCerrar = { mostrarFormularioNueloCliente = false }
        ) {
            FormularioNuevoCliente(
                onCerrar = { mostrarFormularioNueloCliente = false }
            )
        }


        // --- Formulario emergente nuevo vehiculo---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevoVehiculo,
            onCerrar = { mostrarFormularioNuevoVehiculo = false }
        ) {
            FormularioNuevoVehiculo(
                clientePropietario = clienteSeleccionado,
                onCerrar = { mostrarFormularioNuevoVehiculo = false }
            )
        }

    }
}
