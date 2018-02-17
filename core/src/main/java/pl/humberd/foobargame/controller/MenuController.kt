package pl.humberd.foobargame.controller

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer
import com.github.czyzby.autumn.mvc.stereotype.Asset
import com.github.czyzby.autumn.mvc.stereotype.View

/** Thanks to View annotation, this class will be automatically found and initiated.
 *
 * This is application's main view, displaying a menu with several options.  */
@View(id = "menu", value = "ui/templates/menu.lml", themes = arrayOf("music/theme.ogg"))
class MenuController : ViewRenderer {
    /** Asset-annotated files will be found and automatically loaded by the AssetsService.  */
    @Asset("images/libgdx.png")
    private val logo: Texture? = null

    override fun render(stage: Stage, delta: Float) {
        // As a proof of concept that you can pair custom logic with Autumn MVC views, this class implements
        // ViewRenderer and handles view rendering manually. It renders LibGDX logo before drawing the stage.
        stage.act(delta)

        val batch = stage.batch
        batch.color = stage.root.color // We want the logo to share color alpha with the stage.
        batch.begin()
        batch.draw(logo, ((stage.width - logo!!.width).toInt() / 2).toFloat(),
                ((stage.height - logo.height).toInt() / 2).toFloat())
        batch.end()

        stage.draw()
    }
}