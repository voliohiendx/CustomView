package com.example.custtomview.exoPlayer

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle


@BindingAdapter("setPlayerPath", "setLifecycle")
fun LikePlayerView.setPlayerPath(path:String, lifecycle: Lifecycle){
    if (path.isEmpty()){
        this.removePlayer()
    }else{
        this.startPlaying(Uri.parse(path), lifecycle)
    }
}