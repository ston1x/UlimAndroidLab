package com.borutsky.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.borutsky.lab2.databinding.FragmentPostBinding
import com.borutsky.lab2.utils.InjectorUtils
import com.borutsky.lab2.viewmodel.PostViewModel


class PostFragment : Fragment() {

    private val args: PostFragmentArgs by navArgs()

    private val postViewModel: PostViewModel by viewModels {
        InjectorUtils.providePostViewModelFactory(requireContext(), args.postId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.apply {
            viewModel = postViewModel
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
