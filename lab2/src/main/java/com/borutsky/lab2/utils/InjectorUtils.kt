package com.borutsky.lab2.utils

import android.content.Context
import com.borutsky.lab2.data.AppDatabase
import com.borutsky.lab2.data.repository.PostRepository
import com.borutsky.lab2.viewmodel.HomeViewModelFactory
import com.borutsky.lab2.viewmodel.NewPostViewModelFactory
import com.borutsky.lab2.viewmodel.PostViewModelFactory

object InjectorUtils {

    private fun getPostRepository(context: Context): PostRepository =
        PostRepository.getInstance(AppDatabase.getInstance(context.applicationContext).postDao())

    fun provideHomeViewModelFactory(context: Context): HomeViewModelFactory {
        val repository = getPostRepository(context)
        return HomeViewModelFactory(repository)
    }

    fun provideNewPostViewModelFactory(context: Context): NewPostViewModelFactory {
        val repository = getPostRepository(context)
        return NewPostViewModelFactory(repository)
    }

    fun providePostViewModelFactory(context: Context, postId: Long): PostViewModelFactory {
        val repository = getPostRepository(context)
        return PostViewModelFactory(repository, postId)
    }


}

