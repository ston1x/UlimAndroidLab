package com.borutsky.lab2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.borutsky.lab2.databinding.FragmentNewPostBinding
import com.borutsky.lab2.utils.InjectorUtils
import com.borutsky.lab2.utils.hideKeyboard
import com.borutsky.lab2.viewmodel.NewPostViewModel


class NewPostFragment : Fragment() {

    private val newPostViewModel: NewPostViewModel by viewModels {
        InjectorUtils.provideNewPostViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.apply {
            viewModel = newPostViewModel
            binding.hander = object : Handler {
                override fun createNew() {
                    newPostViewModel.createNew()
                }
            }
        }
        binding.lifecycleOwner = this
        subscribeUi()
        return binding.root
    }


    private fun subscribeUi() {
        newPostViewModel.created.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity?.hideKeyboard()
    }

    interface Handler {
        fun createNew()
    }

}
