package com.borutsky.lab2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borutsky.lab2.data.model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getPosts(): LiveData<List<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getById(id: Long): LiveData<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)

}