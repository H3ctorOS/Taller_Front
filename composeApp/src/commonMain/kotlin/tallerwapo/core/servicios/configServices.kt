package tallerwapo.core.servicios

import tallerwapo.core.contexto.AppContexto

object ConfigServices {

    private val config = AppContexto.config

    // --- Valores por defecto ---
    const val ipDefecto = "localhost"
    private const val macDefecto = "00:11:22:33:44:55"

    // --- Inicializar valores al arrancar ---
    fun inicializar() {
        // Leer desde el config o usar valores por defecto
        AppContexto.ipServidor = config.leer("servidor.ip") ?: ipDefecto
        AppContexto.direccionMacServidor = config.leer("servidor.mac") ?: macDefecto

        // Guardar valores por defecto si no existían
        config.escribir("servidor.ip", AppContexto.ipServidor)
        config.escribir("servidor.mac", AppContexto.direccionMacServidor)
    }

    // --- Actualizar IP del servidor ---
    fun actualizarIp(ip: String) {
        AppContexto.ipServidor = ip
        config.escribir("servidor.ip", ip)
    }

    // --- Actualizar MAC del servidor ---
    fun actualizarMac(mac: String) {
        AppContexto.direccionMacServidor = mac
        config.escribir("servidor.mac", mac)
    }

    // --- Restablecer IP al valor por defecto ---
    fun restablecerIp() {
        actualizarIp(ipDefecto)
    }

    // --- Restablecer MAC al valor por defecto ---
    fun restablecerMac() {
        actualizarMac(macDefecto)
    }

    // --- Obtener valores actuales ---
    fun obtenerIp(): String = AppContexto.ipServidor
    fun obtenerMac(): String = AppContexto.direccionMacServidor
}
