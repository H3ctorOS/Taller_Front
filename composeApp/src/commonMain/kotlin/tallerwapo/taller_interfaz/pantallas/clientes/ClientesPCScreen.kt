package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto
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

class ClientesPCScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        // --- Formularios ---
        var mostrarFormularioEditarCliente by remember { mutableStateOf(false) }
        var mostrarFormularioNuevoCliente by remember { mutableStateOf(false) }
        var mostrarFormularioEditarVehiculo by remember { mutableStateOf(false) }
        var mostrarFormularioNuevoVehiculo by remember { mutableStateOf(false) }
        var mostrarFormularioNuevaCita by remember { mutableStateOf(false) }

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
                listaClientes = AppContexto.clientesRepo.buscarTodos()
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
                listaVehiculos = AppContexto.vehiculosRepo.buscarPorCliente(cliente)
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
                    AppContexto.citasRepo.buscarPorVehiculo(vehiculo)
                else AppContexto.citasRepo.buscarTodas()

                listaCitasUI = respuesta.BoRespuesta?.map { CitaBoUI(it) } ?: emptyList()
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
                onClienteSeleccionado = { cliente ->
                    clienteSeleccionado = cliente

                    // Reset dependientes al cambiar cliente
                    vehiculoSeleccionado = null
                    listaVehiculos = emptyList()
                    citaSeleccionadaUI = null
                    listaCitasUI = emptyList()

                    scope.launch { actualizarListaVehiculos(cliente) }
                },
                onClienteDoubleClick = { clienteSeleccionado = it; mostrarFormularioEditarCliente = true },
                modifier = Modifier.width(200.dp).fillMaxHeight(),
                onNewClick = { mostrarFormularioNuevoCliente = true },
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
                        onVehiculoSeleccionado = { vehiculo ->
                            vehiculoSeleccionado = vehiculo

                            // Reset dependientes al cambiar vehículo
                            citaSeleccionadaUI = null
                            listaCitasUI = emptyList()

                            scope.launch { actualizarListaCitas(vehiculo) }
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
            clienteSeleccionado?.let {
                FormularioModificarCliente(it) {
                    mostrarFormularioEditarCliente = false
                    scope.launch { actualizarListaClientes() }
                }
            }
        }

        FormularioEmergente(
            mostrar = mostrarFormularioNuevoCliente,
            onCerrar = { mostrarFormularioNuevoCliente = false }
        ) {
            FormularioNuevoCliente {
                mostrarFormularioNuevoCliente = false
                scope.launch { actualizarListaClientes() }
            }
        }



        // --- Formulario nuevo vehículo ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevoVehiculo,
            onCerrar = { mostrarFormularioNuevoVehiculo = false }
        ) {
            FormularioNuevoVehiculo(clientePropietario = clienteSeleccionado) {
                mostrarFormularioNuevoVehiculo = false

                // Actualizar lista automáticamente
                clienteSeleccionado?.let { cliente ->
                    scope.launch { actualizarListaVehiculos(cliente) }
                }
            }
        }



        // --- Formulario editar vehículo ---
        FormularioEmergente(
            mostrar = mostrarFormularioEditarVehiculo,
            onCerrar = { mostrarFormularioEditarVehiculo = false }
        ) {
            vehiculoSeleccionado?.let { vehiculo ->
                FormularioModificarVehiculo(clienteSeleccionado, vehiculo) {
                    mostrarFormularioEditarVehiculo = false

                    // Actualizar lista automáticamente
                    clienteSeleccionado?.let { cliente ->
                        scope.launch { actualizarListaVehiculos(cliente) }
                    }

                    // Opcional: resetear selección del vehículo editado
                    vehiculoSeleccionado = null
                }
            }
        }



        // --- Formulario nueva cita ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevaCita,
            onCerrar = { mostrarFormularioNuevaCita = false }
        ) {
            vehiculoSeleccionado?.let { vehiculo ->
                FormularioNuevaCita(vehiculo) {
                    mostrarFormularioNuevaCita = false

                    // Actualizar lista de citas automáticamente
                    scope.launch { actualizarListaCitas(vehiculo) }
                }
            }
        }

        // --- Formulario nuevo ingreso ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevoIngreso != null,
            onCerrar = { mostrarFormularioNuevoIngreso = null }
        ) {
            mostrarFormularioNuevoIngreso?.let { citaUI ->
                FormularioNuevoIngreso(cita = citaUI.cita) {
                    mostrarFormularioNuevoIngreso = null

                    // Usar el vehículo actualmente seleccionado para actualizar la lista
                    vehiculoSeleccionado?.let { vehiculo ->
                        scope.launch { actualizarListaCitas(vehiculo) }
                    }
                }
            }
        }

        // --- Formulario nuevo gasto ---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevoGasto != null,
            onCerrar = { mostrarFormularioNuevoGasto = null }
        ) {
            mostrarFormularioNuevoGasto?.let { citaUI ->
                FormularioNuevoGasto(cita = citaUI.cita) {
                    mostrarFormularioNuevoGasto = null

                    // Usar el vehículo actualmente seleccionado para actualizar la lista
                    vehiculoSeleccionado?.let { vehiculo ->
                        scope.launch { actualizarListaCitas(vehiculo) }
                    }
                }
            }
        }



    }

}