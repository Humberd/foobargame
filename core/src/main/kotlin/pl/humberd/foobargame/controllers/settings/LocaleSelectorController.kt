package pl.humberd.foobargame.controllers.settings

import com.badlogic.gdx.scenes.scene2d.Actor
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog
import com.github.czyzby.lml.annotation.LmlAction
import com.github.czyzby.lml.parser.action.ActionContainer
import com.github.czyzby.lml.util.LmlUtilities
import pl.humberd.foobargame.services.LocaleServiceWrapper

@ViewDialog(id = "localeSelector", value = "ui/templates/settings/locale-selector.lml")
class LocaleSelectorController(
        private val localeServiceWrapper: LocaleServiceWrapper
) : ActionContainer {

    @LmlAction("getCurrentLocale")
    fun getCurrentLocale() = localeServiceWrapper.currentLocale.value

    @LmlAction("selectLocale")
    fun selectLocale(actor: Actor) {
        val locale = LmlUtilities.getActorId(actor)
        localeServiceWrapper.selectLocale(locale)
    }
}