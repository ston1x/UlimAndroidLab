package com.borutsky.lab1.utils

import com.borutsky.lab1.viewmodel.HomeViewModelFactory

object InjectorUtils {

    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }

}