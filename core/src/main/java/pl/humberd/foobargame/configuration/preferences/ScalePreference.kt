package pl.humberd.foobargame.configuration.preferences

import com.badlogic.gdx.scenes.scene2d.Actor
import com.github.czyzby.autumn.mvc.component.preferences.dto.AbstractPreference
import com.github.czyzby.autumn.mvc.stereotype.preference.Property
import com.github.czyzby.lml.util.LmlUtilities
import com.kotcrab.vis.ui.VisUI.SkinScale

/** Thanks to the Property annotation, this class will be automatically found and initiated.
 *
 * This class manages VisUI scale preference.  */
@Property("Scale")
class ScalePreference : AbstractPreference<SkinScale>() {
    override fun getDefault(): SkinScale {
        return SkinScale.X2
    }

    override fun extractFromActor(actor: Actor): SkinScale {
        return convert(LmlUtilities.getActorId(actor))
    }

    override fun convert(rawPreference: String): SkinScale {
        return SkinScale.valueOf(rawPreference)
    }

    override fun serialize(preference: SkinScale): String {
        return preference.name
    }
}