package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.*
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.formularios.citas.FormularioNuevaCita
import tallerwapo.taller_interfaz.formularios.clientes.FormularioModificarCliente
import tallerwapo.taller_interfaz.formularios.vehiculos.FormularioModificarVehiculo
import tallerwapo.taller_interfaz.formularios.clientes.FormularioNuevoCliente
import tallerwapo.taller_interfaz.formularios.gastos.FormularioNuevoGasto
import tallerwapo.taller_interfaz.formularios.ingresos.FormularioNuevoIngreso
import tallerwapo.taller_interfaz.formularios.vehiculos.FormularioNuevoVehiculo
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaCitas
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaClientes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaVehiculos
import tallerwapo.taller_interfaz.pantallas.clientes.componentes.ClientesPanelinfo
import tallerwapo.taller_interfaz.themes.AppThemeProvider

class ClientesScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        // --- Formularios ---
        var mostrarFormularioEditarCliente by remember { mutableStateOf(false) }
        var mostrarFormularioNueloCliente by remember { mutableStateOf(false) }
        var mostrarFormularioEditarVehiculo by remember { mutableStateOf(false) }
        var mostrarFormularioNuevoVehiculo by remember { mutableStateOf(false) }
        var mostrarFormularioNuevaCita by remember { mutableStateOf(false) }

        // Formularios de ingreso y gasto
        var mostrarFormularioNuevoIngreso by remember { mutableStateOf<CitaBoUI?>(null) }
        var mostrarFormularioNuevoGasto by remember { mutableStateOf<CitaBoUI?>(null) }

        // --- Estados ---
        var clienteSeleccionado by remember { mutableStateOf<ClienteBO?>(null) }
        var vehiculoSeleccionado by remember { mutableStateOf<VehiculoBO?>(null) }
        var citaSeleccionadaUI by remember { mutableStateOf<CitaBoUI?>(null) }

        // --- Listas ---
        var listaClientes by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }
        var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }
        var listaCitasUI by remember { mutableStateOf<List<CitaBoUI>>(emptyList()) }

        // --- Funciones de actualización ---
        suspend fun actualizarListaClientes() {
            try {
                listaClientes = ApiContexto.clientesRepo.buscarTodos()
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error desconocido",
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }

        suspend fun actualizarListaVehiculos(cliente: ClienteBO) {
            try {
                listaVehiculos = ApiContexto.vehiculosRepo.buscarPorCliente(cliente)
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error al buscar vehículos del cliente: ${cliente.nombre}",
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }

        suspend fun actualizarListaCitas(vehiculo: VehiculoBO?) {
            try {
                val respuesta = if (vehiculo != null)
                    ApiContexto.citasRepo.buscarPorVehiculo(vehiculo)
                else ApiContexto.citasRepo.buscarTodas()

                respuesta.BoRespuesta?.let { boList ->
                    listaCitasUI = boList.map { CitaBoUI(it) }
                }
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error al buscar lista de citas",
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }

        LaunchedEffect(Unit) { actualizarListaClientes() }

        // --- UI principal ---
        Row(modifier = Modifier.fillMaxSize()) {

            // Lista de clientes
            ListaClientes(
                clientes = listaClientes,
                clienteSeleccionado = clienteSeleccionado,
                onClienteSeleccionado = {
                    clienteSeleccionado = it
                    scope.launch { actualizarListaVehiculos(it) }
                },
                onClienteDoubleClick = { clienteSeleccionado = it; mostrarFormularioEditarCliente = true },
                modifier = Modifier.width(200.dp).fillMaxHeight(),
                onNewClick = { mostrarFormularioNueloCliente = true },
                mostrarNew = true
            )

            // Contenido derecho
            Column(modifier = Modifier.fillMaxHeight().weight(1f)) {
                Row {
                    Spacer(Modifier.width(theme.paddingS))

                    // Lista de vehículos
                    ListaVehiculos(
                        vehiculos = listaVehiculos,
                        vehiculoSeleccionado = vehiculoSeleccionado,
                        onVehiculoSeleccionado = {
                            vehiculoSeleccionado = it
                            scope.launch { actualizarListaCitas(it) }
                        },
                        onVehiculoDoubleClick = { vehiculoSeleccionado = it; mostrarFormularioEditarVehiculo = true },
                        modifier = Modifier.width(200.dp),
                        mostrarNew = true,
                        onNewClick = { mostrarFormularioNuevoVehiculo = true }
                    )

                    Spacer(Modifier.width(theme.paddingS))

                    // Lista de citas
                    ListaCitas(
                        listaCitasUI = listaCitasUI,
                        citaSeleccionada = citaSeleccionadaUI,
                        onCitaSeleccionada = { citaSeleccionadaUI = it },
                        onCitaDoubleClick = { /* opcional */ },
                        onNewClick = { mostrarFormularioNuevaCita = true },
                        onNuevoIngresoClick = { citaUI -> mostrarFormularioNuevoIngreso = citaUI },
                        onNuevoGastoClick = { citaUI -> mostrarFormularioNuevoGasto = citaUI },
                        mostrarNew = true,
                        modifier = Modifier.width(600.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Panel info cliente
                ClientesPanelinfo(
                    cliente = clienteSeleccionado,
                    modifier = Modifier.fillMaxWidth().height(80.dp)
                )
            }
        }

        // --- Formularios emergentes ---
        FormularioEmergente(
            mostrar = mostrarFormularioEditarCliente,
            onCerrar = { mostrarFormularioEditarCliente = false }
        ) {
            clienteSeleccionado?.let { FormularioModificarCliente(it) {
                mostrarFormularioEditarCliente = false
                scope.launch { actualizarListaClientes() }
            } }
        }

        FormularioEmergente(
            mostrar = mostrarFormularioNueloCliente,
            onCerrar = { mostrarFormularioNueloCliente = false }
        ) {
            FormularioNuevoCliente {
                mostrarFormularioNueloCliente = false
                scope.launch { actualizarListaClientes() }
            }
        }

        FormularioEmergente(
            mostrar = mostrarFormularioNuevoVehiculo,
            onCerrar = { mostrarFormularioNuevoVehiculo = false }
        ) {
            FormularioNuevoVehiculo(clientePropietario = clienteSeleccionado) {
                mostrarFormularioNuevoVehiculo = false
            }
        }

        FormularioEmergente(
            mostrar = mostrarFormularioEditarVehiculo,
            onCerrar = { mostrarFormularioEditarVehiculo = false }
        ) {
            vehiculoSeleccionado?.let { FormularioModificarVehiculo(clienteSeleccionado, it) {
                mostrarFormularioEditarVehiculo = false
                scope.launch { clienteSeleccionado?.let { actualizarListaVehiculos(it) } }
            } }
        }

        FormularioEmergente(
            mostrar = mostrarFormularioNuevaCita,
            onCerrar = { mostrarFormularioNuevaCita = false }
        ) {
            vehiculoSeleccionado?.let { FormularioNuevaCita(it) {
                mostrarFormularioNuevaCita = false
                scope.launch { actualizarListaCitas(it) }
            } }
        }

        // --- Formulario nuevo ingreso ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevoIngreso != null,
            onCerrar = { mostrarFormularioNuevoIngreso = null }
        ) {
            mostrarFormularioNuevoIngreso?.let { citaUI ->
                FormularioNuevoIngreso(cita = citaUI.cita) { mostrarFormularioNuevoIngreso = null }
            }
        }

        // --- Formulario nuevo gasto ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevoGasto != null,
            onCerrar = { mostrarFormularioNuevoGasto = null }
        ) {
            mostrarFormularioNuevoGasto?.let { citaUI ->
                FormularioNuevoGasto(cita = citaUI.cita) { mostrarFormularioNuevoGasto = null }
            }
        }
    }
}
