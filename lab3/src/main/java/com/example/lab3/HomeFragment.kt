package com.example.lab3


import android.animation.ValueAnimator
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.MediaController
import android.widget.VideoView
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.lab3.databinding.FragmentHomeBinding
import com.example.lab3.utils.InjectorUtils
import com.example.lab3.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory()
    }

    private var job: Job? = null
    private var animator: ValueAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.apply {
            viewModel = homeViewModel
            handler = object : Handler {
                override fun onButtonDialogClick() {
                    buildDialog()
                }
            }
        }
        binding.root.apply {
            prepareVideoView(this.videoView)
        }
        return binding.root
    }

    private fun prepareVideoView(videoView: VideoView) {
        val path = "android.resource://${requireContext().packageName}/${R.raw.shortvideo}"
        videoView.setVideoURI(Uri.parse(path))
        val mediaController = MediaController(requireContext())
        videoView.setMediaController(mediaController)
        videoView.setOnCompletionListener {
            job?.cancel()
            homeViewModel.isPlaying.postValue(false)
        }
        videoView.setOnPreparedListener {
            homeViewModel.duration.postValue(videoView.duration)
            it.setOnSeekCompleteListener {
                videoView.start()
                startSeekBarProgress()
            }
            subscribeUi()
        }
    }

    private fun subscribeUi() {
        homeViewModel.isPlaying.observe(viewLifecycleOwner) { isPlaying ->
            if (isPlaying) {
                videoView.seekTo(homeViewModel.progress.value ?: 0)
            } else {
                animator?.cancel()
                videoView.pause()
            }
        }
    }

    private fun startSeekBarProgress() {
        animator = ValueAnimator.ofInt(
            homeViewModel.progress.value ?: 0,
            homeViewModel.duration.value ?: 0
        )
        animator?.addUpdateListener {
            val value = it.animatedValue as Int
            homeViewModel.progress.postValue(value)
        }
        animator?.interpolator = LinearInterpolator()
        animator?.duration =
            homeViewModel.duration.value?.minus(homeViewModel.progress.value ?: 0)?.toLong() ?: 0
        animator?.start()
    }

    private fun buildDialog() {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Dialog")
            setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            AsyncLayoutInflater(requireContext()).inflate(
                R.layout.alert_dialog,
                null
            ) { view, i, viewGroup ->
                setView(view)
                show()
            }
        }
    }

    interface Handler {
        fun onButtonDialogClick()
    }

}
