package com.borutsky.lab2.data.repository

import com.borutsky.lab2.data.dao.PostDao
import com.borutsky.lab2.data.model.Post

class PostRepository private constructor(
    private val postDao: PostDao
) {

    suspend fun createPost(post: Post) {
        postDao.insert(post)
    }

    fun getAllPosts() = postDao.getPosts()

    fun getPostById(id: Long) = postDao.getById(id)

    companion object {
        @Volatile
        private var instance: PostRepository? = null

        fun getInstance(postDao: PostDao) =
            instance ?: synchronized(this) {
                instance ?: PostRepository(postDao).also { instance = it }
            }
    }

}