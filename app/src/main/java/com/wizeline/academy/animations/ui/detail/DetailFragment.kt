package com.wizeline.academy.animations.ui.detail

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.google.android.material.transition.MaterialSharedAxis
import com.wizeline.academy.animations.R
import com.wizeline.academy.animations.databinding.DetailFragmentBinding
import com.wizeline.academy.animations.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animatetitletransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.title.observe(viewLifecycleOwner) { binding.tvTitle.text = it }
        viewModel.subtitle.observe(viewLifecycleOwner) { binding.tvSubtitle.text = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater, container, false)
        binding.btnMoreDetails.setOnClickListener { goToMoreDetails() }
        binding.ivImageDetail.loadImage(args.imageId)
        return binding.root
    }

    private fun goToMoreDetails() {
        val directions =
            DetailFragmentDirections.toMoreDetailsFragment(args.imageId, viewModel.contentIndex)
        findNavController().navigate(directions)
    }

    private fun animatetitletransition() {
        val titleSlide = Slide(Gravity.TOP).addTarget(R.id.tvTitle).setDuration(500)
        val titleFade = Fade().addTarget(R.id.tvTitle).setDuration(1000)
        val ImageSlide = Slide(Gravity.BOTTOM).addTarget(R.id.ivImageDetail).setDuration(500)
        val ImageScale = Explode()
        val ImageFade = Fade().addTarget(R.id.ivImageDetail).setDuration(1000)
        val subtitleFade = Fade().addTarget(R.id.tvSubtitle).setDuration(700)
        val enterTransitionSet = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(titleSlide)
            addTransition(titleFade)
            addTransition(ImageSlide)
            addTransition(ImageFade)
            addTransition(subtitleFade)
            addTransition(ImageScale)
        }

        enterTransition = enterTransitionSet
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 1000
        }
    }



}