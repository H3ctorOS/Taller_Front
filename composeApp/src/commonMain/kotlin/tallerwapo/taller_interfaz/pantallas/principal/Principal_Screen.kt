package com.tallerwapo.interfazgrafica.taller_interfazgrafica.pantallas.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.tallerwapo.interfazgrafica.taller_interfazgrafica.pantallas.vehiculos.Formulario_NuevoVehiculo

class Principal_Screen : Screen {

    @Composable
    override fun Content() {
        // El navegador principal para cambiar de pantallas etc
        val navigator: Navigator = LocalNavigator.currentOrThrow

        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.safeContentPadding().fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { navigator.push(Formulario_NuevoVehiculo()) }) { Text("Nuevo vehiculo") }
            Spacer(Modifier.height(20.dp))
            Button(onClick = { navigator.push(Formulario_NuevoVehiculo()) }) { Text("Button bar") }
        }
    }
}