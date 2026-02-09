package tallerwapo.core.config

import android.content.Context
import java.io.File


actual object ConfigManager {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    actual fun crear(): ConfigProperties {
        if (!::appContext.isInitialized) {
            throw IllegalStateException(
                "ConfigManager no inicializado. Llama a ConfigManager.init(context)"
            )
        }

        val archivoConfig = File(appContext.filesDir, "config.properties")
        return ConfigProperties(archivoConfig)
    }
}