package tallerwapo.core.utils

import android.content.res.Resources

actual fun getDeviceType(): DeviceType {

    val screenWidthDp = Resources.getSystem().configuration.screenWidthDp

    return when {
        screenWidthDp < 600 -> DeviceType.MOBILE
        screenWidthDp < 1024 -> DeviceType.TABLET
        else -> DeviceType.DESKTOP
    }
}