package tallerwapo.taller_interfaz.pantallas.principal.componentesMovil

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.LocalNavigator
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.pantallas.calendario.DiasSemanaScreen
import tallerwapo.taller_interfaz.pantallas.clientes.ClientesMobileScreen
import tallerwapo.taller_interfaz.pantallas.config.ConfigMobileScreen
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import androidx.compose.material.icons.filled.DirectionsCar
import tallerwapo.taller_interfaz.pantallas.vehiculos.VehiculosMobileScreen

@Composable
fun BottomBarMobile() {

    val navigator = LocalNavigator.current
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    val currentScreen = navigator?.lastItem

    NavigationBar(
        containerColor = theme.backgroundColor
    ) {

        NavigationBarItem(
            selected = currentScreen is DiasSemanaScreen,
            onClick = {
                if (currentScreen !is DiasSemanaScreen) {
                    navigator?.replace(DiasSemanaScreen())
                }
            },
            icon = {
                Icon(Icons.Default.CalendarToday, contentDescription = "Calendario")
            },
            label = { Text("Calendario") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = theme.textoPrincipalColor,
                unselectedIconColor = Color.Gray,
                selectedTextColor = theme.textoPrincipalColor,
                unselectedTextColor = Color.Gray
            )
        )

        NavigationBarItem(
            selected = currentScreen == ClientesMobileScreen,
            onClick = {
                if (currentScreen != ClientesMobileScreen) {
                    navigator?.replace(ClientesMobileScreen)
                }
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Clientes")
            },
            label = { Text("Clientes") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = theme.textoPrincipalColor,
                unselectedIconColor = Color.Gray,
                selectedTextColor = theme.textoPrincipalColor,
                unselectedTextColor = Color.Gray
            )
        )

        NavigationBarItem(
            selected = currentScreen == ConfigMobileScreen,
            onClick = {
                if (currentScreen != ConfigMobileScreen) {
                    navigator?.replace(ConfigMobileScreen)
                }
            },
            icon = {
                Icon(Icons.Default.Settings, contentDescription = "Configuración")
            },
            label = { Text("Config") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = theme.textoPrincipalColor,
                unselectedIconColor = Color.Gray,
                selectedTextColor = theme.textoPrincipalColor,
                unselectedTextColor = Color.Gray
            )
        )

        NavigationBarItem(
            selected = currentScreen is VehiculosMobileScreen && (currentScreen as VehiculosMobileScreen).esModoGlobal,
            onClick = {
                if (currentScreen !is VehiculosMobileScreen || !(currentScreen as VehiculosMobileScreen).esModoGlobal) {
                    navigator?.replace(VehiculosMobileScreen())
                }
            },
            icon = { Icon(Icons.Default.DirectionsCar, contentDescription = "Vehículos") },
            label = { Text("Vehículos") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = theme.textoPrincipalColor,
                unselectedIconColor = Color.Gray,
                selectedTextColor = theme.textoPrincipalColor,
                unselectedTextColor = Color.Gray
            )
        )

    }
}