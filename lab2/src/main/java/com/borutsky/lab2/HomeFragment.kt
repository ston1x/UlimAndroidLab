package com.borutsky.lab2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.borutsky.lab2.adapter.PostAdapter
import com.borutsky.lab2.databinding.FragmentHomeBinding
import com.borutsky.lab2.utils.InjectorUtils
import com.borutsky.lab2.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = PostAdapter(homeViewModel)
        binding.lifecycleOwner = this
        binding.apply {
            recyclerView.adapter = adapter
            viewModel = homeViewModel
            handler = object : Handler {
                override fun add() {
                    findNavController().navigate(R.id.action_homeFragment_to_newPostFragment)
                }

            }
        }
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: PostAdapter, binding: FragmentHomeBinding) {
        homeViewModel.posts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.hasPosts = !it.isNullOrEmpty()
        }
    }

    interface Handler {
        fun add()
    }

    interface Home

}
