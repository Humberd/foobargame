package pl.humberd.foobargame.service

import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Inject
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService
import com.github.czyzby.autumn.mvc.component.ui.SkinService
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.VisUI.SkinScale
import pl.humberd.foobargame.configuration.preferences.ScalePreference

/** Thanks to the ViewActionContainer annotation, this class will be automatically found and processed.
 *
 * This service handles GUI scale.  */
@Component
class ScaleService {
    // @Inject-annotated fields will be automatically filled by the context initializer.
    /** @return scale property, which is saved in application's preferences.
     */
    @Inject
    val preference: ScalePreference? = null
    @Inject
    private val interfaceService: InterfaceService? = null
    @Inject
    private val skinService: SkinService? = null

    /** @return current GUI scale.
     */
    val scale: SkinScale
        get() = preference!!.get()

    /** @return all scales supported by the application.
     */
    val scales: Array<SkinScale>
        get() = SkinScale.values()

    /** @param scale the new application's scale.
     */
    fun changeScale(scale: SkinScale) {
        if (preference!!.get() == scale) {
            return  // This is the current scale.
        }
        preference.set(scale)
        // Changing GUI skin, reloading all screens:
        interfaceService!!.reload {
            // Removing previous skin resources:
            VisUI.dispose()
            // Loading new skin:
            VisUI.load(scale)
            // Replacing the previously default skin:
            skinService!!.clear()
            skinService.addSkin("default", VisUI.getSkin())
        }
    }
}