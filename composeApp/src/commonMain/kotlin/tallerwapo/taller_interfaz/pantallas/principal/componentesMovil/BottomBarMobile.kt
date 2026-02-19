package tallerwapo.taller_interfaz.pantallas.principal.componentesMovil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.pantallas.calendario.DiasSemanaScreen
import tallerwapo.taller_interfaz.pantallas.clientes.ClientesMobileScreen
import tallerwapo.taller_interfaz.pantallas.clientes.ClientesPCScreen
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun BottomBarMobile(
    onScreenSelected: (Screen) -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    var selectedItem by remember { mutableStateOf(BottomBarItemId.CALENDARIO) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(theme.backgroundColor),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        BottomBarItem(
            id = BottomBarItemId.CALENDARIO,
            text = "Calendario",
            selectedItem = selectedItem
        ) {
            selectedItem = BottomBarItemId.CALENDARIO
            onScreenSelected(DiasSemanaScreen())
        }

        BottomBarItem(
            id = BottomBarItemId.CLIENTES,
            text = "Clientes",
            selectedItem = selectedItem
        ) {
            selectedItem = BottomBarItemId.CLIENTES
            onScreenSelected(ClientesMobileScreen)
        }

    }
}

@Composable
private fun BottomBarItem(
    id: BottomBarItemId,
    text: String,
    selectedItem: BottomBarItemId,
    onClick: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val selected = id == selectedItem

    val backgroundColor = if (selected) {
        theme.selectedBackgroundColor.copy(alpha = 0.15f)
    } else {
        theme.backgroundColor
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = theme.title,
            color = theme.textoPrincipalColor
        )
    }
}

enum class BottomBarItemId {
    CALENDARIO,
    CLIENTES
}
