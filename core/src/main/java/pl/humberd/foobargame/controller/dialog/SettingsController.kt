package pl.humberd.foobargame.controller.dialog

import com.badlogic.gdx.scenes.scene2d.Actor
import com.github.czyzby.autumn.annotation.Inject
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog
import com.github.czyzby.lml.annotation.LmlAction
import com.github.czyzby.lml.parser.action.ActionContainer
import com.kotcrab.vis.ui.VisUI.SkinScale
import pl.humberd.foobargame.service.ScaleService

/** This is a settings dialog, which can be shown in any view by using "show:settings" LML action or - in Java code -
 * through InterfaceService.showDialog(Class) method. Thanks to the fact that it implements ActionContainer, its methods
 * will be available in the LML template.  */
@ViewDialog(id = "settings", value = "ui/templates/dialogs/settings.lml")
class SettingsController : ActionContainer {
    // @Inject-annotated fields will be automatically filled with values from the context.
    @Inject
    private val scaleService: ScaleService? = null

    /** @return array of available GUI scales.
     */
    val guiScales: Array<SkinScale>
        @LmlAction("scales")
        get() = scaleService!!.scales

    /** @param actor requested scale change. Its ID represents a GUI scale.
     */
    @LmlAction("changeScale")
    fun changeGuiScale(actor: Actor) {
        val scale = scaleService!!.preference!!.extractFromActor(actor)
        scaleService.changeScale(scale)
    }
}