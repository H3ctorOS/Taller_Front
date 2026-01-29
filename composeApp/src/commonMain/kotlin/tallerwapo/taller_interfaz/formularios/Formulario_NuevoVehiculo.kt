package tallerwapo.taller_interfaz.formularios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import tallerwapo.taller_interfaz.objetos.CampoEntrada.CampoEntradaRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.utils.FormulariosService
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun FormularioNuevoVehiculo(
    onCerrar: () -> Unit
) {
    val vehiculosApi = ApiContexto.vehiculosApi
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
                titulo = "Propietario",
                valor = propietario,
                onValueChange = { propietario = it }
            )

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

            Spacer(Modifier.height(AppTheme.PaddingL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                AppBoton(text = "Cancelar",
                    onClick = { onCerrar()})

                Spacer(Modifier.width(AppTheme.PaddingM))

                AppBoton( text = "Guardar",
                    onClick = {
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
                    })

            }
        }
    }

