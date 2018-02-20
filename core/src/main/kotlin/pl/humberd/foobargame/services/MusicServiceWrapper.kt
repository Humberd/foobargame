package pl.humberd.foobargame.services

import com.badlogic.gdx.audio.Sound
import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.mvc.component.sfx.MusicService
import io.reactivex.subjects.BehaviorSubject

@Component
class MusicServiceWrapper(private val musicService: MusicService) {
    val musicStatus = BehaviorSubject.create<MusicStatus>()
    val soundStatus = BehaviorSubject.create<SoundStatus>()

    @Initiate
    fun init() {
        val initialMusicStatus = MusicStatus(musicService.musicVolume, musicService.isMusicEnabled)
        musicStatus.onNext(initialMusicStatus)

        val initialSoundStatus = SoundStatus(musicService.soundVolume, musicService.isSoundEnabled)
        soundStatus.onNext(initialSoundStatus)
    }

    fun setMusicVolume(volume: Float) {
        musicService.musicVolume = volume
        musicStatus.onNext(musicStatus.value!!.also { it.volume = volume })
    }

    fun setMusicEnabled(enabled: Boolean) {
        musicService.isMusicEnabled = enabled
        musicStatus.onNext(musicStatus.value!!.also { it.enabled = enabled })
    }

    fun setSoundVolume(volume: Float) {
        musicService.soundVolume = volume
        soundStatus.onNext(soundStatus.value!!.also { it.volume = volume })
    }

    fun setSoundEnabled(enabled: Boolean) {
        musicService.isSoundEnabled = enabled
        soundStatus.onNext(soundStatus.value!!.also { it.enabled = enabled })
    }
}

data class MusicStatus(
        var volume: Float,
        var enabled: Boolean
)

data class SoundStatus(
        var volume: Float,
        var enabled: Boolean
)