package tallerwapo.taller_interfaz.themes

import tallerwapo.taller_interfaz.themes.interfaces.AppTheme
import tallerwapo.taller_interfaz.themes.themes.ThemeClaro
import tallerwapo.taller_interfaz.themes.themes.ThemeOscuro

object AppThemeProvider {

    fun getTheme(mode: ThemeMode): AppTheme =
        when (mode) {
            ThemeMode.CLARO -> ThemeClaro
            ThemeMode.OSCURO -> ThemeOscuro
        }
}