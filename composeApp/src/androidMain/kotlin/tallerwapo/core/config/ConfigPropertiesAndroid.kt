package tallerwapo.core.config

import android.content.Context
import java.io.File

object ConfigPropertiesAndroid {

    fun crear(context: Context): ConfigProperties {
        val archivoConfig = File(context.filesDir, "config.properties")
        return ConfigProperties(archivoConfig)
    }
}