package pl.humberd.foobargame.services

import com.github.czyzby.autumn.annotation.Component
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay

@Component
class GooglePlayService {
    val status = BehaviorSubject.createDefault<GooglePlayStatus>(GooglePlayStatus.Disconnected)

    init {
        disconnect()
    }

    fun connect() {
        async {
            status.onNext(GooglePlayStatus.Loading)
            delay(1000)
            status.onNext(GooglePlayStatus.Connected("User"))
        }
    }

    fun disconnect() {
        async {
            status.onNext(GooglePlayStatus.Loading)
            delay(1000)
            status.onNext(GooglePlayStatus.Disconnected)
        }
    }
}

sealed class GooglePlayStatus(val textStatus: String) {
    val i18nKey: String

    init {
        i18nKey = "settings/googlePlay/status/${textStatus.toLowerCase()}"
    }

    object Loading : GooglePlayStatus("Loading")
    object Disconnected : GooglePlayStatus("Disconnected")
    data class Connected(
            val username: String
    ) : GooglePlayStatus("Connected")
}