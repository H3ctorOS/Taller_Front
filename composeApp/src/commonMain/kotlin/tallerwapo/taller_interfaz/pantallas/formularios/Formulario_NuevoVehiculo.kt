package tallerwapo.taller_interfaz.pantallas.formularios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import tallerwapo.taller_interfaz.pantallas.formularios.objetos.CampoEntradaRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.utils.FormulariosService
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun FormularioNuevoVehiculo(
    onCerrar: () -> Unit,
    cliente: ClienteBO?
) {
    val vehiculosApi = ApiContexto.vehiculosApi
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    var propietario by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .widthIn(max = 900.dp)
                .background(AppTheme.Surface, AppTheme.CornerRadius)
                .padding(AppTheme.PaddingM)
        ) {
            Text(
                text = "Nuevo vehículo",
                style = AppTheme.Title
            )

            Spacer(Modifier.height(AppTheme.PaddingL))

            CampoEntradaRow(
                label = "Propietario",
                value = propietario,
                onValueChange = { propietario = it }
            )

            CampoEntradaRow(
                label = "Matrícula",
                value = matricula,
                onValueChange = { matricula = it }
            )

            CampoEntradaRow(
                label = "Marca",
                value = marca,
                onValueChange = { marca = it }
            )

            CampoEntradaRow(
                label = "Modelo",
                value = modelo,
                onValueChange = { modelo = it }
            )

            CampoEntradaRow(
                label = "Observaciones",
                value = observaciones,
                onValueChange = { observaciones = it }
            )

            Spacer(Modifier.height(AppTheme.PaddingL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {onCerrar()}) {
                    Text("Cancelar")
                }

                Spacer(Modifier.width(AppTheme.PaddingM))

                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        val vehiculo = VehiculoBO(
                            uuid = 0,
                            uuidPropietario = 1,
                            matricula = matricula,
                            marca = marca,
                            modelo = modelo,
                            codigoEstado = "activo"
                        )

                        Logs.info(this, "Creando nuevo vehículo")

                        val respuesta = vehiculosApi.crearVehiculo(vehiculo)
                        FormulariosService.gestionarRespuestaApi(respuesta){onCerrar() }
                    }

                }){ Text("Guardar") }
            }
        }
    }

