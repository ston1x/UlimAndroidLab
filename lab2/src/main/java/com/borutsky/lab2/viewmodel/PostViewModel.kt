package com.borutsky.lab2.viewmodel

import androidx.lifecycle.ViewModel
import com.borutsky.lab2.data.repository.PostRepository

class PostViewModel internal constructor(private val postRepository: PostRepository, postId: Long) :
    ViewModel() {

    val post = postRepository.getPostById(postId)

}
