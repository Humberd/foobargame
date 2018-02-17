package pl.humberd.foobargame.configuration

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.GdxRuntimeException
import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService
import com.github.czyzby.autumn.mvc.component.ui.SkinService
import com.github.czyzby.autumn.mvc.stereotype.preference.LmlParserSyntax
import com.github.czyzby.autumn.mvc.stereotype.preference.Preference
import com.github.czyzby.lml.parser.LmlParser
import com.github.czyzby.lml.parser.impl.tag.Dtd
import com.github.czyzby.lml.vis.parser.impl.VisLmlSyntax
import com.kotcrab.vis.ui.VisUI

@Component
class Configuration {
    @Preference
    val preferencesPath = Constants.PREFERENCES_FILE_NAME

    @LmlParserSyntax
    val syntax = VisLmlSyntax()

    @Initiate
    fun initiatefConfiguration(
            skinService: SkinService,
            interfaceService: InterfaceService) {
        VisUI.load(VisUI.SkinScale.X2)

        skinService.addSkin("default", VisUI.getSkin())

        saveDtdSchema(interfaceService.parser, Gdx.files.local("lml.dtd"))
    }

    fun saveDtdSchema(lmlParser: LmlParser, fileHandle: FileHandle) {
        try {
            val appendable = fileHandle.writer(false, "UTF-8")
            val strict = lmlParser.isStrict
            lmlParser.isStrict = false // Temporary setting to non-strict to generate as much tags as possible.
            Dtd.saveSchema(lmlParser, appendable)
            appendable.close()
            lmlParser.isStrict = strict
        } catch (exception: Exception) {
            throw GdxRuntimeException("Unable to save DTD schema.", exception)
        }

    }

}