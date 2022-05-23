package com.wizeline.academy.animations.ui.splash_screen

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.google.android.material.transition.MaterialSharedAxis
import com.wizeline.academy.animations.R
import com.wizeline.academy.animations.databinding.SplashFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateLogo()
        animateColorBackground()
        animateLogotransition()

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(3000)
            goToHomeScreen()
        }
    }

    private fun animateLogo() {
        binding.ivWizelineLogo.alpha = 0f
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 2000
        animator.interpolator = OvershootInterpolator()
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            binding.ivWizelineLogo.alpha = animatedValue
            binding.ivWizelineLogo.scaleX = animatedValue
            binding.ivWizelineLogo.scaleY = animatedValue
        }
        animator.start()
    }
    private fun animateLogotransition() {
        val logoSlide = Slide(Gravity.TOP).addTarget(R.id.ivWizelineLogo).setDuration(1000)
        val logoFade = Fade().addTarget(R.id.ivWizelineLogo).setDuration(2000)
        val enterTransitionSet = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(logoSlide)
            addTransition(logoFade)
        }
        enterTransition = enterTransitionSet
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 1000
        }
    }

    private fun animateColorBackground() {
        val objectAnimator = ObjectAnimator.ofObject(
            binding.container,
            "backgroundColor",
            ArgbEvaluator(),
            context?.let { ContextCompat.getColor(it, R.color.bones) },
            context?.let { ContextCompat.getColor(it, R.color.black) }
        )
        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = 1500
        objectAnimator.start()

    }



    private fun goToHomeScreen() {
        val directions = SplashFragmentDirections.toMainFragment()
        findNavController().navigate(directions)
    }
}