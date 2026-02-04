package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.citas.FormularioNuevaCita
import tallerwapo.taller_interfaz.formularios.clientes.FormularioModificarCliente
import tallerwapo.taller_interfaz.formularios.vehiculos.FormularioModificarVehiculo
import tallerwapo.taller_interfaz.formularios.clientes.FormularioNuevoCliente
import tallerwapo.taller_interfaz.formularios.vehiculos.FormularioNuevoVehiculo
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaCitas
import tallerwapo.taller_interfaz.pantallas.clientes.componentes.ClientesPanelinfo
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaClientes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaVehiculos
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

        // --- Estados ---
        var clienteSeleccionado by remember { mutableStateOf<ClienteBO?>(null) }
        var vehiculoSeleccionado by remember { mutableStateOf<VehiculoBO?>(null) }
        var citaSeleccionada by remember { mutableStateOf<CitaBO?>(null) }

        // --- Listas de desplegables---
        var listaClientes by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }
        var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }
        var listaCitas by remember { mutableStateOf<List<CitaBO>>(emptyList()) }


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
                if (recibida?.BoRespuesta != null) listaVehiculos = recibida.BoRespuesta

            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: ("Ha habido algun problema a buscar la lista de vehiculos del cliente: " + cliente.nombre),
                    botones = listOf(MensajesEmergentes.BotonDialogo("Ok") {})
                )
            }
        }


        // --- Función para actualizar la lista de citas---
        suspend fun actualizarListaCitas(vehiculo: VehiculoBO?) {
            try {
                var respuestaRecibida : RespuestaDTO <List<CitaBO>>

                if(vehiculo != null) {
                    respuestaRecibida = ApiContexto.citasRepo.buscarPorVehiculo(vehiculo)
                }else{
                    respuestaRecibida = ApiContexto.citasRepo.buscarTodas()
                }

                if (respuestaRecibida.BoRespuesta != null) listaCitas = respuestaRecibida.BoRespuesta

            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: ("Ha habido algun problema a buscar la lista citas"),
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
                modifier = Modifier.width(200.dp).fillMaxHeight(),
                onNewClick = {
                    mostrarFormularioNueloCliente = true
                },
                mostrarNew = true
            )


            // --- Resto contenido a la derecha---
            Column(modifier = Modifier.fillMaxHeight().weight(1f)) {

                Row {
                    Spacer(Modifier.width(theme.paddingS))

                    // Lista de coches del cliente seleccionado
                    ListaVehiculos(
                        vehiculos = listaVehiculos,
                        vehiculoSeleccionado = vehiculoSeleccionado,
                        onVehiculoSeleccionado = { vehiculoSeleccionado = it
                            scope.launch {
                                actualizarListaCitas(it)
                            }
                                                 },
                        onVehiculoDoubleClick = { vehiculo ->
                            vehiculoSeleccionado = vehiculo
                            mostrarFormularioEditarVehiculo = true
                        },
                        modifier = Modifier.width(200.dp),
                        mostrarNew = true,
                        onNewClick = {mostrarFormularioNuevoVehiculo = true}
                    )

                    Spacer(Modifier.width(theme.paddingS))

                    // Lista de citas del coche del cliente seleccionado
                    ListaCitas(
                        listaCitas = listaCitas,
                        citaSeleccionada = citaSeleccionada,
                        onCitaSeleccionada = { citaSeleccionada = it},
                        onCitaDoubleClick = {  },
                        onNewClick = { mostrarFormularioNuevaCita = true },
                        mostrarNew = true,
                        modifier = Modifier.width(200.dp)
                    )
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
                FormularioModificarCliente(
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
                onCerrar = { mostrarFormularioNueloCliente = false
                    scope.launch {
                        val idSeleccionado = clienteSeleccionado?.uuid
                        actualizarListaClientes()
                        clienteSeleccionado = listaClientes.find { it.uuid == idSeleccionado }
                    }
                }
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


        // --- Formulario emergente para editar vehiculo ---
        FormularioEmergente(
            mostrar = mostrarFormularioEditarVehiculo,
            onCerrar = { mostrarFormularioEditarVehiculo = false }
        ) {
            vehiculoSeleccionado?.let { vehiculo ->
                FormularioModificarVehiculo(clienteSeleccionado,
                    vehiculo,
                    onCerrar = {
                        mostrarFormularioEditarVehiculo = false

                        // 🔹 Actualizar lista usando el scope
                        scope.launch {
                            clienteSeleccionado?.let{actualizarListaVehiculos(clienteSeleccionado!!)}
                        }
                    }
                )
            }
        }


        // --- Formulario emergente para nueva cita---
        FormularioEmergente(
            mostrar = mostrarFormularioNuevaCita,
            onCerrar = { mostrarFormularioNuevaCita = false }
        ) {
            vehiculoSeleccionado?.let { vehiculo ->
                FormularioNuevaCita(vehiculoSeleccionado,
                    onCerrar = {
                        mostrarFormularioNuevaCita = false

                        scope.launch {
                            vehiculoSeleccionado?.let{actualizarListaCitas(vehiculoSeleccionado)}
                        }
                    }
                )
            }
        }



    }
}
