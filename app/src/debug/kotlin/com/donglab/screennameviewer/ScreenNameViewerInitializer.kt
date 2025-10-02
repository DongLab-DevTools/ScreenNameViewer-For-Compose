import android.app.Application
import android.graphics.Color
import android.view.Gravity
import com.donglab.screennameviewer.publicapi.dsl.initScreenNameViewer

object ScreenNameViewerInitializer {

    fun init(application: Application) {
        initScreenNameViewer(application) {
            settings {
                debugModeCondition = false
                enableCondition = false
            }

            config {
                textStyle {
                    size = 10f
                    color = Color.BLUE
                }
                background {
                    color = Color.argb(80, 255, 255, 255)
                    padding = 20
                }
                position {
                    topMargin = 52
                    activity = Gravity.TOP or Gravity.START
                    fragment = Gravity.TOP or Gravity.END
                    composeRoute = Gravity.TOP or Gravity.END
                }
            }
        }
    }
}