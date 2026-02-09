package tallerwapo.core.config

import java.io.File

object ConfigPropertiesPC {

    fun crear(): ConfigProperties {
        // Directorio raíz de la aplicación (donde se ejecuta el jar / app)
        val directorioBase = File(System.getProperty("user.dir"))

        val archivoConfig = File(directorioBase, "config.properties")

        return ConfigProperties(archivoConfig)
    }
}