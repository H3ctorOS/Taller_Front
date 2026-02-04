package tallerwapo.taller_interfaz.objetos.listables.listas

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.MasBoton
import tallerwapo.taller_interfaz.objetos.listables.ListableBOList
import tallerwapo.taller_interfaz.objetos.listables.items.CitasListItem
import tallerwapo.taller_interfaz.objetos.listables.items.ClientesListItem
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun ListaCitas(
    listaCitas: List<CitaBO>,
    citaSeleccionada: CitaBO?,
    onCitaSeleccionada: (CitaBO) -> Unit,
    onCitaDoubleClick: ((CitaBO) -> Unit)? = null,
    onNewClick: () -> Unit,
    mostrarNew: Boolean = false,
    modifier: Modifier = Modifier
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Box(modifier = modifier) {
        Column{
            Row (
                verticalAlignment = Alignment.Bottom   //alinea hijos abajo
            ){
                AppTextos(text = "Citas", style = theme.title,modifier = Modifier.alignByBaseline() )
                Spacer(Modifier.width(theme.paddingS))
                if (mostrarNew) { MasBoton(onClick = { onNewClick() }, modifier = Modifier.alignByBaseline()) }
            }

            ListableBOList(
                items = listaCitas.map { CitasListItem(it) },
                selectedItemId = citaSeleccionada?.uuid,
                onItemClick = onCitaSeleccionada,
                onItemDoubleClick = onCitaDoubleClick,
                modifier = Modifier.fillMaxSize().weight(1F)
            )
        }
    }
}
