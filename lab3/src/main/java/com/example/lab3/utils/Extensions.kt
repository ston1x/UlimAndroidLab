package com.example.lab3.utils

import android.annotation.SuppressLint
import android.widget.TextView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter("videoProgress")
fun bindVideoProgress(videoView: VideoView, progress: Int) {
    videoView.seekTo(progress)
    videoView.start()
}

@SuppressLint("SetTextI18n")
@BindingAdapter("progressTime")
fun bindProgressTime(textView: TextView, progress: Int) {
    val seconds = progress / 1000
    val mins = seconds / 60
    textView.text = String.format("%02d:%02d", mins, seconds)
}

@BindingAdapter("isPlaying")
fun bindPlaying(lottieAnimationView: LottieAnimationView, isPlaying: Boolean) {
    if (isPlaying) {
        lottieAnimationView.speed = 1f
    } else {
        lottieAnimationView.speed = -1f
    }
    lottieAnimationView.playAnimation()
}