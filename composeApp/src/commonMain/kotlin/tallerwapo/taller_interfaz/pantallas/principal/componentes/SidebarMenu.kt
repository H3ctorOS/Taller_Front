package tallerwapo.taller_interfaz.pantallas.principal.componentes


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.pantallas.clientes.ClientesScreen
import tallerwapo.taller_interfaz.pantallas.pruebas.PruebasScreen
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun AppSidebar(
    onScreenSelected: (Screen) -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    var selectedItem by remember { mutableStateOf(SidebarItemId.CALENDARIO) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(theme.backgroundColor)
            .padding(theme.paddingM)
    ) {
        Spacer(Modifier.height(theme.paddingL))

        SidebarItem(
            id = SidebarItemId.CALENDARIO,
            "Calendario",
            selectedItem = selectedItem
        ) {
            selectedItem = SidebarItemId.CALENDARIO
            onScreenSelected(PruebasScreen())
        }

        Spacer(Modifier.height(theme.paddingL))

        SidebarItem(
            id = SidebarItemId.CLIENTES,
            "Clientes",
            selectedItem = selectedItem
        ) {
            selectedItem = SidebarItemId.CLIENTES
            onScreenSelected(ClientesScreen())
        }


        Spacer(Modifier.height(theme.paddingL))

        SidebarItem(
            id = SidebarItemId.VEHICULOS,
            "Vehiculos",
            selectedItem = selectedItem
        ) {
            selectedItem = SidebarItemId.VEHICULOS

        }

        Spacer(Modifier.height(theme.paddingL))

        SidebarItem(
            id = SidebarItemId.CITAS,
            "Citas",
            selectedItem = selectedItem
        ) {
            selectedItem = SidebarItemId.CITAS

        }

    }
}

@Composable
private fun SidebarItem(
    id: SidebarItemId,
    text: String,
    selectedItem: SidebarItemId,
    onClick: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    val selected = id == selectedItem

    val backgroundColor = if (selected) {
        theme.selectedBackgroundColor.copy(alpha = 0.15f)
    } else {
        theme.backgroundColor
    }

    Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = theme.cornerRadius)
            .clickable {
                onClick()
            }
    ) {
        Text(text = text,
            style = theme.title,
            color = theme.textoPrincipalColor,
            modifier = Modifier.fillMaxWidth().padding(
                horizontal = theme.paddingM,
                vertical = theme.paddingS
            ),

        )
    }
}



enum class SidebarItemId {
    CALENDARIO,
    CLIENTES,
    VEHICULOS,
    CITAS
}