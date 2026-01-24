package tallerwapo.taller_interfaz.pantallas.principal.componentes


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.taller_interfaz.pantallas.pruebas.PruebasScreen
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun AppSidebar(
    onScreenSelected: (Screen) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(AppTheme.Surface)
            .padding(AppTheme.PaddingM)
    ) {
        Text("TallerWapo", style = AppTheme.Title)
        Spacer(Modifier.height(AppTheme.PaddingL))

        SidebarItem("Pantalla de pruebas") { onScreenSelected(PruebasScreen()) }

    }
}

@Composable
private fun SidebarItem(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.PaddingS)
            .clickable { onClick() }
    ) {
        Text(text = text, style = AppTheme.Body, color = AppTheme.TextoSecundario)
    }
}