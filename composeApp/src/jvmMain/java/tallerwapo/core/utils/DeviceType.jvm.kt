package tallerwapo.core.utils

actual fun getDeviceType(): DeviceType {
    // En PC siempre será Desktop
    return DeviceType.DESKTOP
}