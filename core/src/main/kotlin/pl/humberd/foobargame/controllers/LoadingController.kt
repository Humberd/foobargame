package pl.humberd.foobargame.controllers

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.czyzby.autumn.mvc.component.asset.AssetService
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer
import com.github.czyzby.autumn.mvc.stereotype.View
import com.github.czyzby.lml.annotation.LmlActor
import com.kotcrab.vis.ui.widget.VisProgressBar

@View(value = "ui/templates/loading.lml", first = true)
class LoadingController(
        val assetService: AssetService
) : ViewRenderer {

    @LmlActor("loadingBar")
    lateinit var loadingBar: VisProgressBar

    override fun render(stage: Stage, delta: Float) {
        assetService.update()
        loadingBar.value = assetService.loadingProgress
        stage.act(delta)
        stage.draw()
    }

}