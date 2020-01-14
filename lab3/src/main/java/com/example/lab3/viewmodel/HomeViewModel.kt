package com.example.lab3.viewmodel

import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val progress = MutableLiveData(0)
    val isPlaying = MutableLiveData(false)
    val duration = MutableLiveData(0)

    fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            this.progress.postValue(progress)
        }
    }

    fun onStartTrackingTouch(seekBar: SeekBar) {
        isPlaying.postValue(false)
    }

    fun onStopTrackingTouch(seekBar: SeekBar) {
        isPlaying.postValue(true)
    }

    fun onClickPlayButton(view: View) {
        isPlaying.postValue(isPlaying.value?.not())
    }


}