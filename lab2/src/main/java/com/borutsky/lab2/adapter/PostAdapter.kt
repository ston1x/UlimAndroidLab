package com.borutsky.lab2.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.borutsky.lab2.HomeFragmentDirections
import com.borutsky.lab2.data.model.Post
import com.borutsky.lab2.databinding.ListItemPostBinding
import com.borutsky.lab2.viewmodel.HomeViewModel
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class PostAdapter(private val homeViewModel: HomeViewModel) :
    ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            homeViewModel
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder(
        private val binding: ListItemPostBinding,
        private val homeViewModel: HomeViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var job: Job? = null

        init {
            binding.setTouchListener { v, event ->
                binding.post?.let { post ->
                    return@setTouchListener when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            v.performClick()
                            job = homeViewModel.viewModelScope.launch(Dispatchers.IO) {
                                delay(300)
                                homeViewModel.setPreviewPost(post)
                            }
                            true
                        }
                        MotionEvent.ACTION_UP -> {
                            if(event.eventTime - event.downTime < 300) {
                                job?.cancel()
                                job = null
                                navigateToPost(post, v)
                            } else {
                                homeViewModel.setPreviewPost(null)
                                job = null
                            }
                            true
                        }
                        else -> false
                    }
                } ?: return@setTouchListener false
            }
        }

        private fun navigateToPost(post: Post, it: View) {
            val direction = HomeFragmentDirections.actionHomeFragmentToPostFragment(post.id)
            it.findNavController().navigate(direction)
        }

        fun bind(item: Post) {
            binding.apply {
                post = item
                executePendingBindings()
            }
        }
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}