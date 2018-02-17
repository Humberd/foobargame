package pl.humberd.foobargame.configuration

import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.mvc.component.ui.SkinService
import com.github.czyzby.autumn.mvc.stereotype.preference.LmlParserSyntax
import com.github.czyzby.autumn.mvc.stereotype.preference.Preference
import com.github.czyzby.lml.util.Lml
import com.github.czyzby.lml.vis.parser.impl.VisLmlSyntax
import com.kotcrab.vis.ui.VisUI

@Component
class Configuration {
    @Preference
    val preferencesPath = Constants.PREFERENCES_FILE_NAME

    @LmlParserSyntax
    val syntax = VisLmlSyntax()

    @Initiate
    fun initiateConfiguration(skinService: SkinService) {
        VisUI.load(VisUI.SkinScale.X2)

        skinService.addSkin("default", VisUI.getSkin())

        Lml.EXTRACT_UNANNOTATED_METHODS = false
    }

}