package pl.humberd.foobargame.controllers.settings

import com.badlogic.gdx.assets.loaders.I18NBundleLoader
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.I18NBundle
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.context.Context
import com.github.czyzby.autumn.mvc.component.i18n.LocaleService
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer
import com.github.czyzby.autumn.mvc.stereotype.View
import com.github.czyzby.lml.annotation.LmlAction
import com.github.czyzby.lml.annotation.LmlActor
import com.github.czyzby.lml.annotation.LmlAfter
import com.github.czyzby.lml.parser.action.ActionContainer
import com.kotcrab.vis.ui.widget.VisLabel
import pl.humberd.foobargame.services.FacebookService
import pl.humberd.foobargame.services.FacebookStatus

@View(id = "settingsView", value = "ui/templates/settings/settings.lml")
class SettingsController(
        val facebookService: FacebookService,
        val localeService: LocaleService
) : ActionContainer, ViewRenderer {

    @LmlActor("facebookStatusLabel")
    lateinit var facebookStatusLabel: VisLabel

    @LmlAfter
    fun init() {
        facebookService.status
                .subscribe {
                    facebookStatusLabel.setText(localeService.i18nBundle[it.i18nKey])
                }
    }

    @Initiate
    fun foo(context: Context) {
        println("hello")
    }

    @LmlAction("facebookStatusToggle")
    fun facebookStatusToggle() {
        when (facebookService.status.value) {
            is FacebookStatus.Loading -> println("do nothing")
            is FacebookStatus.Disconnected -> facebookService.connect()
            is FacebookStatus.Connected -> facebookService.disconnect()
        }

    }

    override fun render(stage: Stage, delta: Float) {
        stage.act(delta)
        stage.draw()
    }
}