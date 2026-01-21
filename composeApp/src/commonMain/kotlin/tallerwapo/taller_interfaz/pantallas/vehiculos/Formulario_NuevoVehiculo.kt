package com.tallerwapo.interfazgrafica.taller_interfazgrafica.pantallas.vehiculos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.tallerwapo.interfazgrafica.taller_interfazgrafica.objetosPantallas.formulario.CampoEntradaRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.utils.Logs

class Formulario_NuevoVehiculo : Screen {

    //Estilos de fuentes
    val TAMANIOA_FUENTE_CAMPOS  = 20.sp;
    val ESTILO_FUENTE = FontStyle.Italic;
    val ESTILO_TEXTO_CAMPOS = TextStyle.Default.copy(fontSize = TAMANIOA_FUENTE_CAMPOS);

    //Colores
    val COLOR_ETIQUETAS = Color.White;

    val vehiculosApi = ApiContexto.vehiculosApi

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        var propietario: String by remember { mutableStateOf("ghfghfgfh") }
        var matricula: String by remember { mutableStateOf("") }
        var modelo: String by remember { mutableStateOf("") }
        var marca: String by remember { mutableStateOf("") }
        var observaciones: String by remember { mutableStateOf("") }

        Box(modifier = Modifier.background(Color.Gray)) {
            Column(modifier = Modifier.safeContentPadding().fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Nuevo Vehiculo", color = COLOR_ETIQUETAS,fontStyle = ESTILO_FUENTE, fontSize = TAMANIOA_FUENTE_CAMPOS)

                CampoEntradaRow(label = "Propietario", value = propietario, onValueChange = { propietario = it })
                CampoEntradaRow(label = "Matricula", value = matricula, onValueChange = { matricula = it })
                CampoEntradaRow(label = "Marca", value = marca, onValueChange = { marca = it })
                CampoEntradaRow(label = "Modelo", value = modelo, onValueChange = { modelo = it })
                CampoEntradaRow(label = "Observaciones", value = observaciones, onValueChange = { observaciones = it })

                Button(onClick = { navigator.pop()}, modifier = Modifier.fillMaxWidth() ) { Text("Volver") }

                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        //Crear el vehiculo
                        var vehiculo = VehiculoBO(
                            uuid = 0,
                            uuidPropietario = 1,
                            matricula = "mierdas",
                            marca = "mierdas",
                            modelo = "mierdas",
                            codigoEstado = "roto"
                        )
                        //llamar a la api
                        Logs.info(this,"Pulsado boton crear nuevo vehiculo")
                        vehiculosApi.crearVehiculo(vehiculo)

                    } }, modifier = Modifier.fillMaxWidth() ) { Text("Guardar") }
            }
        }
    }


}