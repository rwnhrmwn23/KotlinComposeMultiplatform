package navigation

import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize

sealed class NavTarget : Parcelable {

    @Parcelize
    data object MainScreen : NavTarget()

    @Parcelize
    data object Screen1 : NavTarget()

    @Parcelize
    data class Screen2(val name: String) : NavTarget()

    @Parcelize
    data object Screen3 : NavTarget()

    @Parcelize
    data object SearchLocationScreen : NavTarget()

    @Parcelize
    data object ReversedLocationScreen : NavTarget()
}