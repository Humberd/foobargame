package pl.humberd.foobargame.controller.action

import com.github.czyzby.autumn.mvc.stereotype.ViewActionContainer
import com.github.czyzby.lml.annotation.LmlAction
import com.github.czyzby.lml.parser.action.ActionContainer

/** Since this class implements ActionContainer and is annotated with ViewActionContainer, its methods will be reflected
 * and available in all LML templates. Note that this class is a component like any other, so it can inject any fields,
 * use Initiate-annotated methods, etc.  */
@ViewActionContainer("global")
class Global : ActionContainer {
    /** This is a mock-up method that does nothing. It will be available in LML templates through "close" (annotation
     * argument) and "noOp" (method name) IDs.  */
    @LmlAction("close")
    fun noOp() {
    }
}