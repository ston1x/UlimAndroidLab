package com.borutsky.lab1.viewmodel

import android.animation.ValueAnimator
import android.view.animation.BounceInterpolator
import android.widget.SeekBar
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val alpha = MutableLiveData(0f)
    val rotation = MutableLiveData(0)

    fun onAlphaChange(seekBar: SeekBar, value: Int, fromUser: Boolean) {
        alpha.postValue(value / 100f)
    }

    fun changeVisibility() {
        alpha.value?.run {
            val startValue = this
            val endValue = if (this > 0) 0f else 1f
            val valueAnimator = ValueAnimator.ofFloat(startValue, endValue)
            valueAnimator.interpolator = BounceInterpolator()
            valueAnimator.duration = 1000
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                alpha.value = value
            }
            valueAnimator.start()
        }
    }

    fun rotateABit() {
        val startValue = rotation.value ?: 0
        val endValue = rotation.value?.plus(90) ?: 0
        val valueAnimator = ValueAnimator.ofInt(startValue, endValue)
        valueAnimator.interpolator = FastOutLinearInInterpolator()
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            var value = it.animatedValue as Int
            value = if (value == 360) 0 else value
            rotation.value = value
        }
        valueAnimator.start()
    }

}