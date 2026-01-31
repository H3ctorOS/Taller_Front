package tallerwapo.taller_interfaz.objetos.listables.listas

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.InterfazContext
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
    modifier: Modifier = Modifier
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(theme.paddingM)
        ) {
            AppTextos(text = "Vehículos", style = theme.title)

            ListableBOList(
                items = vehiculos.map { VehiculoListItem(it) },
                selectedItemId = vehiculoSeleccionado?.uuid,
                onItemClick = onVehiculoSeleccionado,
                onItemDoubleClick = onVehiculoDoubleClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}