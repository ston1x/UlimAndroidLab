package com.borutsky.lab2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.borutsky.lab2.data.repository.PostRepository

class HomeViewModelFactory(private val repository: PostRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(repository) as T

}