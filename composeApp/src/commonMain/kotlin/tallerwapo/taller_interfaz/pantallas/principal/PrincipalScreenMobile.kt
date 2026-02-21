@file:OptIn(ExperimentalMaterial3Api::class)

package tallerwapo.taller_interfaz.pantallas.principal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import tallerwapo.taller_interfaz.pantallas.principal.componentesMovil.BottomBarMobile

/**
 * Pantalla principal optimizada para móviles
 * Layout vertical, menú simplificado y navegación tipo Android
 */
object PrincipalScreenMobile : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Principal Móvil") }
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Bienvenido a la versión móvil",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    // Ejemplo: navegar a otra pantalla
                    // navigator?.push(OtraPantalla())
                }) {
                    Text("Ir a otra pantalla")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Aquí puedes colocar tu contenido simplificado para móviles",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}