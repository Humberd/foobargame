package pl.humberd.foobargame.services

import com.github.czyzby.autumn.annotation.Component
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay

@Component
class FacebookService {
    val status = BehaviorSubject.createDefault<FacebookStatus>(FacebookStatus.Disconnected)

    init {
        disconnect()
    }

    fun connect() {
        async {
            status.onNext(FacebookStatus.Loading)
            delay(1000)
            status.onNext(FacebookStatus.Connected("User"))
        }
    }

    fun disconnect() {
        async {
            status.onNext(FacebookStatus.Loading)
            delay(1000)
            status.onNext(FacebookStatus.Disconnected)
        }
    }
}

sealed class FacebookStatus(val textStatus: String) {
    val i18nKey: String

    init {
        i18nKey = "settings/facebook/status/${textStatus.toLowerCase()}"
    }

    object Loading : FacebookStatus("Loading")
    object Disconnected : FacebookStatus("Disconnected")
    data class Connected(
            val username: String
    ) : FacebookStatus("Connected")
}