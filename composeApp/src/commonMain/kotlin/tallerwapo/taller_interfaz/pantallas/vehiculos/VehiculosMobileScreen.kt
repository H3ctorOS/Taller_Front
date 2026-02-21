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
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaVehiculos
import tallerwapo.taller_interfaz.pantallas.principal.componentesMovil.BottomBarMobile
import tallerwapo.taller_interfaz.themes.AppThemeProvider

class VehiculosMobileScreen(
    private val cliente: ClienteBO? = null
) : Screen {

    /** Propiedad pública para indicar si estamos mostrando todos los vehículos (modo global) */
    val esModoGlobal: Boolean
        get() = cliente == null

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var listaVehiculos by remember { mutableStateOf<List<VehiculoBO>>(emptyList()) }

        suspend fun actualizarListaVehiculos() {
            try {
                listaVehiculos = if (cliente != null) {
                    AppContexto.vehiculosRepo.buscarPorCliente(cliente)
                } else {
                    AppContexto.vehiculosRepo.buscarTodos()
                }
            } catch (e: Exception) {
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Error",
                    mensaje = e.message ?: "Error al cargar vehículos",
                    botones = listOf(
                        MensajesEmergentes.BotonDialogo("Ok") {}
                    )
                )
            }
        }

        LaunchedEffect(Unit) {
            actualizarListaVehiculos()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (cliente != null)
                                "Vehículos de ${cliente.nombre}"
                            else
                                "Todos los vehículos"
                        )
                    },
                    navigationIcon = {
                        if (cliente != null) {
                            IconButton(onClick = { navigator?.pop() }) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Atrás"
                                )
                            }
                        }
                    }
                )
            },
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

                ListaVehiculos(
                    vehiculos = listaVehiculos,
                    vehiculoSeleccionado = null,
                    onVehiculoSeleccionado = {},
                    onVehiculoDoubleClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    mostrarNew = false,
                    onNewClick = {}
                )
            }
        }
    }
}