package com.borutsky.lab2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.borutsky.lab2.data.repository.PostRepository

class PostViewModelFactory(private val repository: PostRepository, private val postId: Long) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        PostViewModel(repository, postId) as T

}