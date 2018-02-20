package pl.humberd.foobargame.controllers.settings

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.czyzby.autumn.annotation.Destroy
import com.github.czyzby.autumn.mvc.component.i18n.LocaleService
import com.github.czyzby.autumn.mvc.component.sfx.MusicService
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer
import com.github.czyzby.autumn.mvc.stereotype.View
import com.github.czyzby.lml.annotation.LmlAction
import com.github.czyzby.lml.annotation.LmlActor
import com.github.czyzby.lml.annotation.LmlAfter
import com.github.czyzby.lml.parser.action.ActionContainer
import com.kotcrab.vis.ui.widget.VisLabel
import pl.humberd.foobargame.extensions.toPercentString
import pl.humberd.foobargame.services.*

@View(id = "settingsView", value = "ui/templates/settings/settings.lml")
class SettingsController(
        val facebookService: FacebookService,
        val localeService: LocaleService,
        val googlePlayService: GooglePlayService,
        val musicServiceWrapper: MusicServiceWrapper
) : ActionContainer, ViewRenderer {

    //------------- facebook handler

    @LmlActor("facebookStatusLabel")
    lateinit var facebookStatusLabel: VisLabel

    @LmlAfter
    fun facebookInit() {
        facebookService.status
                .subscribe {
                    facebookStatusLabel.setText(localeService.i18nBundle[it.i18nKey])
                    // TODO: background color https://stackoverflow.com/a/38881685/4256929
                    when (facebookService.status.value) {
                        is FacebookStatus.Loading -> facebookStatusLabel.color = Color.WHITE
                        is FacebookStatus.Disconnected -> facebookStatusLabel.color = Color.RED
                        is FacebookStatus.Connected -> facebookStatusLabel.color = Color.GREEN
                    }
                }
    }

    @LmlAction("facebookStatusToggle")
    fun facebookStatusToggle() {
        when (facebookService.status.value) {
            is FacebookStatus.Loading -> println("do nothing")
            is FacebookStatus.Disconnected -> facebookService.connect()
            is FacebookStatus.Connected -> facebookService.disconnect()
        }
    }

    //-------------- google play handler

    @LmlActor("googlePlayStatusLabel")
    lateinit var googlePlayStatusLabel: VisLabel

    @LmlAfter()
    fun googlePlayInit() {
        googlePlayService.status
                .subscribe {
                    googlePlayStatusLabel.setText(localeService.i18nBundle[it.i18nKey])
                    // TODO: background color https://stackoverflow.com/a/38881685/4256929
                    when (googlePlayService.status.value) {
                        is GooglePlayStatus.Loading -> googlePlayStatusLabel.color = Color.WHITE
                        is GooglePlayStatus.Disconnected -> googlePlayStatusLabel.color = Color.RED
                        is GooglePlayStatus.Connected -> googlePlayStatusLabel.color = Color.GREEN
                    }
                }
    }

    @LmlAction("googlePlayStatusToggle")
    fun googlePlayStatusToggle() {
        when (googlePlayService.status.value) {
            is GooglePlayStatus.Loading -> println("do nothing")
            is GooglePlayStatus.Disconnected -> googlePlayService.connect()
            is GooglePlayStatus.Connected -> googlePlayService.disconnect()
        }
    }

    //--------------- music

    @LmlActor("musicStatusLabel")
    lateinit var musicStatusLabel: VisLabel

    @LmlAfter
    fun musicInit() {
        musicServiceWrapper.musicStatus
                .subscribe {
                    if (it.enabled) {
                        musicStatusLabel.setText(it.volume.toPercentString())
                    } else {
                        musicStatusLabel.setText(localeService.i18nBundle["settings/music/off"])
                    }
                }
    }

    //--------------- sound

    @LmlActor("soundStatusLabel")
    lateinit var soundStatusLabel: VisLabel

    @LmlAfter
    fun soundInit() {
        musicServiceWrapper.soundStatus
                .subscribe {
                    if (it.enabled) {
                        soundStatusLabel.setText(it.volume.toPercentString())
                    } else {
                        soundStatusLabel.setText(localeService.i18nBundle["settings/sound/off"])
                    }
                }
    }

    override fun render(stage: Stage, delta: Float) {
        stage.act(delta)
        stage.draw()
    }
}