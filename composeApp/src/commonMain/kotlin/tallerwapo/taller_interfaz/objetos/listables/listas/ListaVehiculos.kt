package tallerwapo.taller_interfaz.objetos.listables.listas

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.MasBoton
import tallerwapo.taller_interfaz.objetos.listables.ListableBOList
import tallerwapo.taller_interfaz.objetos.listables.items.VehiculoListItem
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun ListaVehiculos(
    vehiculos: List<VehiculoBO>,
    vehiculoSeleccionado: VehiculoBO?,
    onVehiculoSeleccionado: (VehiculoBO) -> Unit,
    onVehiculoDoubleClick: ((VehiculoBO) -> Unit)? = null,
    onNewClick: () -> Unit,
    mostrarNew: Boolean = false,
    modifier: Modifier = Modifier
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Box(modifier = modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.Bottom   //alinea hijos abajo
            ) {
                AppTextos(text = "Vehículos", style = theme.title,modifier = Modifier.alignByBaseline())
                Spacer(Modifier.width(theme.paddingS))
                if (mostrarNew) { MasBoton(onClick = { onNewClick() }, modifier = Modifier.alignByBaseline()) }
            }

            ListableBOList(
                items = vehiculos.map { VehiculoListItem(it) },
                selectedItemId = vehiculoSeleccionado?.uuid,
                onItemClick = onVehiculoSeleccionado,
                onItemDoubleClick = onVehiculoDoubleClick
            )
        }
    }
}