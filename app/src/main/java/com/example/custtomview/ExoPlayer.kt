package com.example.custtomview

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.StyledPlayerView


interface CurrentPositionCallback {
    fun onCurrentPositionChanged(duration: Long)
}

interface ExoPlayerController {

    fun onStop()

    fun onPlay()

    fun onPause()

    fun onNext()

    fun onPrevious()

    fun onSeekChanged(positionMillis: Long)

    fun setPlaylist(uris: List<Uri>, initIndexPlay: Int = 0, isPlayWhenReady: Boolean = true)

    fun setSingleSong(uri: Uri, isPlayWhenReady: Boolean = true, isRepeat: Boolean = false)

    fun getCurrentPosition(): Long

    fun getDuration(): Long

    fun registerCurrentPositionChanged(
        currentPositionCallback: CurrentPositionCallback
    )

    fun unregisterCurrentPositionChanged(currentPositionCallback: CurrentPositionCallback)

    fun addPlayerCallback(playerCallback: Player.Listener)

    fun setPreview(playerView: StyledPlayerView)

    fun isPlaying(): Boolean

    fun release()
}

class ExoPlayer(
    private val context: Context,
    private val isPlayWhenReady: Boolean = false,
    private val timeDelayUpdateMs: Long = 1000,
    private val playerCallback: Player.Listener? = null,
) : ExoPlayerController, Player.Listener {

    private val listPlayerCallback = mutableListOf<Player.Listener>()

    private val currentDurationCallback = mutableListOf<CurrentPositionCallback>()

    private val handle = Handler(Looper.getMainLooper())

    private val updateCurrentDurationRunnable = object : Runnable {
        override fun run() {
            currentDurationCallback.forEach {
                it.onCurrentPositionChanged(player.currentPosition)
            }
            handle.postDelayed(this, timeDelayUpdateMs)
        }
    }

    val player by lazy {
        val renderFactory = DefaultRenderersFactory(context)
        val defaultExtractorsFactory = DefaultExtractorsFactory()
        defaultExtractorsFactory.setConstantBitrateSeekingEnabled(true)
        val mediaSourceFactory = DefaultMediaSourceFactory(context, defaultExtractorsFactory)
        ExoPlayer.Builder(context, renderFactory, mediaSourceFactory).build().apply {
            playWhenReady = isPlayWhenReady
            playerCallback?.let { listPlayerCallback.add(it) }
            addListener(this@ExoPlayer)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)

        listPlayerCallback.forEach {
            it.onIsPlayingChanged(isPlaying)
        }

        if (isPlaying) {
            handle.post(updateCurrentDurationRunnable)
        } else {
            handle.removeCallbacks(updateCurrentDurationRunnable)
        }
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        listPlayerCallback.forEach {
            it.onIsLoadingChanged(isLoading)
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        listPlayerCallback.forEach {
            it.onPlaybackStateChanged(playbackState)
            currentDurationCallback.forEach {
                it.onCurrentPositionChanged(player.currentPosition)
            }
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        listPlayerCallback.forEach {
            it.onPlayerError(error)
        }
    }

    override fun onStop() = player.stop()

    override fun onPlay() = player.play()


    override fun onPause() = player.pause()


    override fun onNext() = player.seekToNext()


    override fun onPrevious() = player.seekToPrevious()


    override fun onSeekChanged(positionMillis: Long) = player.seekTo(positionMillis)


    override fun setPlaylist(uris: List<Uri>, initIndexPlay: Int, isPlayWhenReady: Boolean) {
        player.apply {
            setMediaItems(uris.map { MediaItem.fromUri(it) })
            seekTo(initIndexPlay, 0)
            playWhenReady = isPlayWhenReady
            playWhenReady = isPlayWhenReady
            prepare()
        }
    }


    override fun setSingleSong(uri: Uri, isPlayWhenReady: Boolean, isRepeat: Boolean) {
        player.apply {
            setMediaItem(MediaItem.fromUri(uri))
            playWhenReady = isPlayWhenReady
            repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
            prepare()
        }
    }


    override fun getCurrentPosition() = player.currentPosition


    override fun getDuration() = player.duration


    override fun registerCurrentPositionChanged(
        currentPositionCallback: CurrentPositionCallback
    ) {
        this.currentDurationCallback.add(currentPositionCallback)
    }

    override fun unregisterCurrentPositionChanged(currentPositionCallback: CurrentPositionCallback) {
        this.currentDurationCallback.remove(currentPositionCallback)
    }

    override fun addPlayerCallback(playerCallback: Player.Listener) {
        listPlayerCallback.add(playerCallback)
    }

    override fun setPreview(playerView: StyledPlayerView) {
        playerView.player = player
    }

    override fun isPlaying(): Boolean = player.isPlaying

    override fun release() {
        player.release()
    }


    fun isFinished() : Boolean {
        return player.playbackState == Player.STATE_ENDED
    }

}