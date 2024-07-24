package com.example.custtomview.exoPlayer

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.custtomview.R
import com.example.custtomview.databinding.ExoSimplePlayerViewBinding
import com.example.custtomview.gone
import com.example.custtomview.loadImage
import com.example.custtomview.visible
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

class LikePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var binding : ExoSimplePlayerViewBinding =
        ExoSimplePlayerViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var player: ExoPlayer? = null
    var isAttach = false
    private var lifeCircleObserver: DefaultLifecycleObserver? = null

    init {
        initExoPlayer()
    }

    private fun initExoPlayer() {
        player = ExoPlayer(context, true, 200, object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when(playbackState){
                    Player.STATE_IDLE, Player.STATE_BUFFERING->{
//                        binding.image.visible(true)
                    }
                    Player.STATE_READY->{
//                        binding.image.gone(true)
                    }
                    Player.STATE_ENDED -> {}
                }
            }
        })
        player?.setPreview(binding.surfaceView)
        binding.surfaceView.setOnClickListener {
            if (player?.isPlaying() == true){
                pauseVideo()
            }else{
                playVideo()
            }
        }
    }

    fun startPlaying(path: Uri, lifecycle: Lifecycle) {
        checkExoPlayerNull()
        player?.setSingleSong(path, isPlayWhenReady = false, isRepeat = true)
        addObserver( lifecycle)
    }

    private fun addObserver(lifecycle: Lifecycle) {
        lifeCircleObserver?.let {
            lifecycle.removeObserver(it)
        }
        lifeCircleObserver = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if (isAttach){
                    playVideo()
                }
            }
            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                pauseVideo()
            }
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                releaseExo()
            }
        }.apply {
            lifecycle.addObserver(this)
        }
    }

    fun removePlayer() {
        player?.player?.playWhenReady = false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAttach = false
        pauseVideo()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttach = true
        playVideo()
    }
    fun loadPhoto(photo:String){
//        binding.image.loadImage(url = photo)
    }
    fun releaseExo() {
        player?.release()
        player = null
    }
    fun playVideo(){
        checkExoPlayerNull()
        player?.setVolume(1f)
        player?.onPlay()
        binding.ivPlay.gone(true)
    }
    fun pauseVideo(){
        player?.setVolume(0f)
        player?.onPause()
        binding.ivPlay.visible(true)
    }
    private fun checkExoPlayerNull(){
        if (player == null) {
            initExoPlayer()
        }
    }
}
