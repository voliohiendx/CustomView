package com.example.custtomview

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.ui.StyledPlayerView

@BindingAdapter("setPlayerPath")
fun LikePlayerView.setPlayerPath(path:String){
    Log.d("Na000007x", "String: ${this.hashCode()}")
    this.startPlaying(Uri.parse(path))
}

@BindingAdapter("invisible")
fun View.invisible(isInvisible: Boolean) {
    visibility = if (isInvisible) View.INVISIBLE else View.GONE
}