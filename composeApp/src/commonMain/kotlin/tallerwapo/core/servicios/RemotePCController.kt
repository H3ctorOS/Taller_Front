package tallerwapo.core.servicios

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Control remoto de PCs Windows:
 * - Wake-on-LAN (enciende el PC si la placa de red soporta WOL)
 * - Apagar o reiniciar remotamente (requiere permisos de administrador en el equipo destino)
 *
 * Nota: Para shutdown/reboot remoto, el firewall de Windows debe permitir el acceso remoto y
 * el usuario debe tener privilegios para apagar/reiniciar el equipo.
 */
object RemotePCController {

    // ----------------- WAKE-ON-LAN -----------------
    fun wakeOnLan(macAddress: String) {
        val macBytes = getMacBytes(macAddress)
        val packet = ByteArray(6 + 16 * macBytes.size)
        for (i in 0 until 6) packet[i] = 0xFF.toByte()
        for (i in 6 until packet.size step macBytes.size)
            System.arraycopy(macBytes, 0, packet, i, macBytes.size)

        val address = InetAddress.getByName("255.255.255.255")
        DatagramSocket().use { socket ->
            socket.broadcast = true
            socket.send(DatagramPacket(packet, packet.size, address, 9))
        }
        println("Magic Packet enviado a $macAddress")
    }

    private fun getMacBytes(macStr: String): ByteArray {
        val hex = macStr.split("[:-]".toRegex())
        require(hex.size == 6) { "MAC inválida." }
        return ByteArray(6) { i -> hex[i].toInt(16).toByte() }
    }

    // ----------------- APAGAR / REINICIAR REMOTO WINDOWS -----------------
    fun shutdownRemote(host: String) {
        executeCommand("shutdown /s /m \\\\$host /t 0 /f", "apagado")
    }

    fun rebootRemote(host: String) {
        executeCommand("shutdown /r /m \\\\$host /t 0 /f", "reinicio")
    }

    private fun executeCommand(command: String, action: String) {
        try {
            ProcessBuilder("cmd", "/c", command).start()
            println("Comando de $action enviado: $command")
        } catch (e: Exception) {
            println("Error al enviar comando de $action: ${e.message}")
        }
    }
}