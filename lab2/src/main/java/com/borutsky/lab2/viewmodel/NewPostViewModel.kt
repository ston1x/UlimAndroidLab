package com.borutsky.lab2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borutsky.lab2.data.model.Post
import com.borutsky.lab2.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewPostViewModel internal constructor(private val postRepository: PostRepository) :
    ViewModel() {

    val title = MutableLiveData("")
    val created = MutableLiveData(false)
    val imageUrl = MutableLiveData<String>("")

    fun createNew() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.createPost(
                Post(
                    title = title.value ?: "",
                    imageUrl = imageUrl.value ?: ""
                )
            )
            created.postValue(true)
        }
    }

}