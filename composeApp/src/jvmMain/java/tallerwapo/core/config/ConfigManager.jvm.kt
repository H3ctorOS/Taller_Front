package tallerwapo.core.config

import java.io.File

actual object ConfigManager {

    actual fun crear(): ConfigProperties {
        // Carpeta base donde se ejecuta la app
        val directorioBase = File(System.getProperty("user.dir"))

        // Obtener unidad (ej: "C:") en Windows
        val unidad = directorioBase.absolutePath.substringBefore(File.separator)

        // Carpeta fija: TallerWapo\Configuracion
        val carpetaConfig = File("$unidad${File.separator}tallerWapo${File.separator}Configuracion")
        if (!carpetaConfig.exists()) {
            carpetaConfig.mkdirs() // crea toda la estructura si no existe
        }

        // Archivo final
        val archivoConfig = File(carpetaConfig, "ConfigUI.properties")

        return ConfigProperties(archivoConfig)
    }
}
