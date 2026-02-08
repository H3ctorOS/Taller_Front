package tallerwapo.taller_interfaz.objetos.listables.listas

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.botones.MasBoton
import tallerwapo.taller_interfaz.objetos.listables.ListableBOList
import tallerwapo.taller_interfaz.objetos.listables.items.CitasListItem
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

@Composable
fun ListaCitas(
    listaCitasUI: List<CitaBoUI>, // <-- ahora recibimos la versión UI
    citaSeleccionada: CitaBoUI?,
    onCitaSeleccionada: (CitaBoUI) -> Unit,
    onCitaDoubleClick: ((CitaBoUI) -> Unit)? = null,
    onNewClick: () -> Unit,
    onNuevoIngresoClick: (CitaBoUI) -> Unit,
    onNuevoGastoClick: (CitaBoUI) -> Unit,
    mostrarNew: Boolean = false,
    modifier: Modifier = Modifier
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Box(modifier = modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.Bottom // alinea hijos abajo
            ) {
                AppTextos(
                    text = "Citas",
                    style = theme.title,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.width(theme.paddingS))
                if (mostrarNew) {
                    MasBoton(onClick = { onNewClick() }, modifier = Modifier.alignByBaseline())
                }
            }

            ListableBOList(
                items = listaCitasUI.map {
                    CitasListItem(
                        bo = it,
                        onNuevoIngresoClick = onNuevoIngresoClick,
                        onNuevoGastoClick = onNuevoGastoClick
                    )
                },
                selectedItemId = citaSeleccionada?.uuid,
                onItemClick = onCitaSeleccionada,
                onItemDoubleClick = onCitaDoubleClick,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}
