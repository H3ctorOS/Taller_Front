package tallerwapo.core.utils

object Logs {

    // Log normal
    fun info(caller: Any, mensaje: String) {
        println("[INFO] [${caller::class.simpleName}] $mensaje")
    }

    // Log de advertencia
    fun warning(caller: Any, mensaje: String) {
        println("[WARN] [${caller::class.simpleName}] $mensaje")
    }

    // Log de error
    fun error(caller: Any, mensaje: String) {
        println("[ERROR] [${caller::class.simpleName}] $mensaje")
    }

    // Log de depuración
    fun debug(caller: Any, mensaje: String) {
        println("[DEBUG] [${caller::class.simpleName}] $mensaje")
    }
}