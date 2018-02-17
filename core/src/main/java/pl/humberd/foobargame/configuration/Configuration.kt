package pl.humberd.foobargame.configuration

import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.mvc.component.ui.SkinService
import com.github.czyzby.autumn.mvc.stereotype.preference.AvailableLocales
import com.github.czyzby.autumn.mvc.stereotype.preference.I18nBundle
import com.github.czyzby.autumn.mvc.stereotype.preference.I18nLocale
import com.github.czyzby.autumn.mvc.stereotype.preference.LmlMacro
import com.github.czyzby.autumn.mvc.stereotype.preference.LmlParserSyntax
import com.github.czyzby.autumn.mvc.stereotype.preference.Preference
import com.github.czyzby.autumn.mvc.stereotype.preference.StageViewport
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.MusicEnabled
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.MusicVolume
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.SoundEnabled
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.SoundVolume
import com.github.czyzby.kiwi.util.gdx.asset.lazy.provider.ObjectProvider
import com.github.czyzby.lml.parser.LmlSyntax
import com.github.czyzby.lml.util.Lml
import com.github.czyzby.lml.vis.parser.impl.VisLmlSyntax
import com.kotcrab.vis.ui.VisUI
import pl.humberd.foobargame.Core
import pl.humberd.foobargame.service.ScaleService

/** Thanks to the Component annotation, this class will be automatically found and processed.
 *
 * This is a utility class that configures application settings.  */
@Component
class Configuration {
    /** Path to the internationalization bundle.  */
    @I18nBundle
    private val bundlePath = "i18n/bundle"
    /** Enabling VisUI usage.  */
    @LmlParserSyntax
    private val syntax = VisLmlSyntax()
    /** Parsing macros available in all views.  */
    @LmlMacro
    private val globalMacro = "ui/templates/macros/global.lml"
    /** Using a custom viewport provider - Autumn MVC defaults to the ScreenViewport, as it is the only viewport that
     * doesn't need to know application's targeted screen size. This provider overrides that by using more sophisticated
     * FitViewport that works on virtual units rather than pixels.  */
    @StageViewport
    private val viewportProvider = ObjectProvider<Viewport> { FitViewport(Core.WIDTH.toFloat(), Core.HEIGHT.toFloat()) }

    /** These sound-related fields allow MusicService to store settings in preferences file. Sound preferences will be
     * automatically saved when the application closes and restored the next time it's turned on. Sound-related methods
     * methods will be automatically added to LML templates - see settings.lml template.  */
    @SoundVolume(preferences = PREFERENCES)
    private val soundVolume = "soundVolume"
    @SoundEnabled(preferences = PREFERENCES)
    private val soundEnabled = "soundOn"
    @MusicVolume(preferences = PREFERENCES)
    private val musicVolume = "musicVolume"
    @MusicEnabled(preferences = PREFERENCES)
    private val musicEnabledPreference = "musicOn"

    /** These i18n-related fields will allow LocaleService to save game's locale in preferences file. Locale changing
     * actions will be automatically added to LML templates - see settings.lml template.  */
    @I18nLocale(propertiesPath = PREFERENCES, defaultLocale = "en")
    private val localePreference = "locale"
    @AvailableLocales
    private val availableLocales = arrayOf("en", "pl")

    /** Setting the default Preferences object path.  */
    @Preference
    private val preferencesPath = PREFERENCES

    /** Thanks to the Initiate annotation, this method will be automatically invoked during context building. All
     * method's parameters will be injected with values from the context.
     *
     * @param scaleService contains current GUI scale.
     * @param skinService contains GUI skin.
     */
    @Initiate
    fun initiateConfiguration(scaleService: ScaleService, skinService: SkinService) {
        // Loading default VisUI skin with the selected scale:
        VisUI.load(scaleService.scale)
        // Registering VisUI skin with "default" name - this skin will be the default one for all LML widgets:
        skinService.addSkin("default", VisUI.getSkin())
        // Thanks to this setting, only methods annotated with @LmlAction will be available in views, significantly
        // speeding up method look-up:
        Lml.EXTRACT_UNANNOTATED_METHODS = false
    }


    companion object {
        /** Name of the application's preferences file.  */
        const val PREFERENCES = "foobargame"
    }
}