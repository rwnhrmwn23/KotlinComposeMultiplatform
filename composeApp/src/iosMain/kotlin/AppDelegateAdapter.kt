import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "AppDelegateAdapter")
class AppDelegateAdapter {
    @OptIn(ExperimentalForeignApi::class)
    fun application(
        application: UIApplication,
        didFinishLaunchingWithOptions: Map<Any?, *>?
    ): Boolean {
        println("first launch from android")

//        NFX.sharedInstance().start()
        return true
    }
}