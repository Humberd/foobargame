package pl.humberd.foobargame.services

import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.mvc.component.i18n.LocaleService
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.async

@Component
class LocaleServiceWrapper(private val localeService: LocaleService) {
    val currentLocale = BehaviorSubject.create<String>()

    @Initiate
    fun init() {
        selectLocale(localeService.currentLocale.language)
    }

    fun selectLocale(locale: String) {
        async {
            localeService.currentLocale = LocaleService.toLocale(locale)
            currentLocale.onNext(locale)
        }
    }
}