package com.example.lab3.utils

import com.example.lab3.viewmodel.HomeViewModelFactory

object InjectorUtils {

    fun provideHomeViewModelFactory(): HomeViewModelFactory =
        HomeViewModelFactory()

}