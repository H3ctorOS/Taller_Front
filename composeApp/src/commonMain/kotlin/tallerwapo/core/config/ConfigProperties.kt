package tallerwapo.core.config

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

class ConfigProperties(
    private val archivo: File
) {

    private val properties = Properties()

    init {
        if (!archivo.exists()) {
            archivo.parentFile?.mkdirs()
            archivo.createNewFile()
        }
        cargar()
    }

    private fun cargar() {
        FileInputStream(archivo).use {
            properties.load(it)
        }
    }

    fun leer(clave: String, defecto: String? = null): String? {
        return properties.getProperty(clave, defecto)
    }

    fun escribir(clave: String, valor: String) {
        properties.setProperty(clave, valor)
        guardar()
    }

    fun eliminar(clave: String) {
        properties.remove(clave)
        guardar()
    }

    fun existe(clave: String): Boolean {
        return properties.containsKey(clave)
    }

    private fun guardar() {
        FileOutputStream(archivo).use {
            properties.store(it, "Archivo de configuración")
        }
    }
}
