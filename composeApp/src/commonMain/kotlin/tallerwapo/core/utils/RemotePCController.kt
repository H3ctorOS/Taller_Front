package tallerwapo.core.utils

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * - Wake-on-LAN (enciende el PC si la placa de red soporta WOL)
 */
object RemotePCController {

    // ----------------- WAKE-ON-LAN -----------------
    suspend fun wakeOnLan(macAddress: String) {
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

}