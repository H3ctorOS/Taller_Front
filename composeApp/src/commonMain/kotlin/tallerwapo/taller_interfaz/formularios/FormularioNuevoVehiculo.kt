package tallerwapo.taller_interfaz.formularios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.objetos.CampoEntrada.CampoEntradaRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.utils.FormulariosService
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.CampoEntrada.SeleccionableRow
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun FormularioNuevoVehiculo(
    clientePropietario: ClienteBO? = null,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    val vehiculosRepo = ApiContexto.vehiculosRepo
    val scope = rememberCoroutineScope()

    var listaPropietarios by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }


    var propietarioSeleccionado by remember { mutableStateOf<ClienteBO?>(clientePropietario) }
    var matricula by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }


    // ───────── Cargar propietarios ─────────
    LaunchedEffect(Unit) {
        if (clientePropietario == null) {
            val listaRecibida = ApiContexto.clientesRepo.buscarTodosLosClientes()
            if (listaRecibida != null) listaPropietarios = listaRecibida
        }
    }


    // ───────── Validación del formulario ─────────
    fun formularioEsValido(): Boolean {
        return propietarioSeleccionado != null &&
                matricula.isNotBlank()
    }


        Column(
            modifier = Modifier
                .widthIn(max = 900.dp)
                .background(theme.surfaceColor, theme.cornerRadius)
                .padding(theme.paddingM)
        ) {
            Text(
                text = "Nuevo vehículo",
                style = theme.title
            )

            Spacer(Modifier.height(theme.paddingL))

            // ───────── PROPIETARIO (Seleccionable) ─────────
            if (clientePropietario == null) {
                SeleccionableRow(
                    titulo = "Propietario",
                    items = listaPropietarios,
                    seleccionado = propietarioSeleccionado,
                    onSeleccionChange = { propietarioSeleccionado = it },
                    labelProvider = { it.nombre }
                )
            } else {
                CampoEntradaRow(
                    titulo = "Propietario",
                    valor = clientePropietario.nombre,
                    onValueChange = {},
                    enabled = false
                )
            }

            CampoEntradaRow(
                titulo = "Matrícula",
                valor = matricula,
                onValueChange = { matricula = it }
            )

            CampoEntradaRow(
                titulo = "Marca",
                valor = marca,
                onValueChange = { marca = it }
            )

            CampoEntradaRow(
                titulo = "Modelo",
                valor = modelo,
                onValueChange = { modelo = it }
            )

            CampoEntradaRow(
                titulo = "Observaciones",
                valor = observaciones,
                onValueChange = { observaciones = it }
            )

            Spacer(Modifier.height(theme.paddingL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                AppBoton(text = "Cancelar",
                    onClick = { onCerrar()})

                Spacer(Modifier.width(theme.paddingM))

                AppBoton( text = "Guardar", enabled = formularioEsValido(),
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            val vehiculo = VehiculoBO(
                                uuid = 0,
                                uuidPropietario = propietarioSeleccionado!!.uuid,
                                matricula = matricula,
                                marca = marca,
                                modelo = modelo
                            )

                            Logs.info(this, "Creando nuevo vehículo")

                            val respuesta = vehiculosRepo.crearVehiculo(vehiculo)
                            FormulariosService.gestionarRespuestaApi(respuesta){onCerrar() }
                        }
                    }
                )

            }
        }

    }

