package tallerwapo.taller_interfaz.pantallas.principal.componentes


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(theme.backgroundColor)
            .padding(theme.paddingM)
    ) {
        Text("TallerWapo", style = theme.title)

        Spacer(Modifier.height(theme.paddingL))

        SidebarItem("Pantalla de pruebas") { onScreenSelected(PruebasScreen()) }
        SidebarItem("Clientes") { onScreenSelected(ClientesScreen()) }

    }
}

@Composable
private fun SidebarItem(
    text: String,
    onClick: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = theme.paddingS)
            .clickable { onClick() }
    ) {
        Text(text = text, style = theme.bodyText, color = theme.textoSecundarioColor)
    }
}