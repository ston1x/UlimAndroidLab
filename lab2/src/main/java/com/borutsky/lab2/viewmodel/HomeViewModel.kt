package com.borutsky.lab2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.borutsky.lab2.data.model.Post
import com.borutsky.lab2.data.repository.PostRepository

class HomeViewModel internal constructor(postRepository: PostRepository) : ViewModel() {

    val posts: LiveData<List<Post>> = postRepository.getAllPosts()
    val previewPost = MutableLiveData<Post>(null)

    fun setPreviewPost(post: Post?) {
        previewPost.postValue(post)
    }

}