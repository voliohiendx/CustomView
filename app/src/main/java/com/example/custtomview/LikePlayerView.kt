package com.example.custtomview

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AdViewProvider
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.io.File

class LikePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var videoSurfaceView: View?
    var player: ExoPlayer? = null

    init {

        val playerLayoutId: Int = R.layout.exo_simple_player_view
        LayoutInflater.from(context).inflate(playerLayoutId, this@LikePlayerView)
        descendantFocusability = FrameLayout.FOCUS_AFTER_DESCENDANTS

        // Content frame.
        videoSurfaceView = findViewById(R.id.surface_view)

        initExoPlayer()
    }

    private fun initExoPlayer() {
        player = ExoPlayer(context, true, 200, object : Player.Listener {

        })
        player?.setPreview(videoSurfaceView as StyledPlayerView)

    }

    fun startPlaying(path: Uri) {
        Log.d("Na000007x", "startPlaying: ${this.hashCode()}")
        Log.d("Na000007x", "player: ${player}\n\n")
        player?.setSingleSong(path, false, true)
        player?.player?.play()
    }

    fun removePlayer() {
        player?.player?.playWhenReady= false
    }

}