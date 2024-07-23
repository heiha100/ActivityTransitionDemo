package com.entertech.www.transitiondemo

import android.R.attr.bitmap
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView


class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enterTransition = CustomTransition().apply {
//            duration = 2000
//            addTarget(R.id.main)
//        }
//        enterTransition = Slide().apply {
//            duration = 2000
//            slideEdge = Gravity.END
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SecondFragment()
    }
}

class CustomTransition(private val context: Context) : Transition() {

    companion object {
        private const val DEFAULT_SCALE_DOWN_FACTOR: Int = 8
        private const val PROPNAME_SCALE_X: String = "transitions:scale_down:scale_x"
        private const val PROPNAME_TRANSLATE_Y: String = "transitions:scale_down:tran_y"
        private const val PROPNAME_SCALE_Y: String = "transitions:scale_down:scale_y"
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        Log.d(
            "CustomTransition",
            "root: $sceneRoot, startValues: ${startValues?.view}, endValues: ${endValues?.view}"
        )

        if (null == endValues || startValues == null) {
            return null
        }
        val view = endValues.view
        if (view.tag == "background") {
            val scaleX = startValues.values[PROPNAME_SCALE_X] as Float
            val scaleY = startValues.values[PROPNAME_SCALE_Y] as Float

            val targetScaleX = endValues.values[PROPNAME_SCALE_X] as Float
            val targetScaleY = endValues.values[PROPNAME_SCALE_Y] as Float
            val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, targetScaleX, scaleX)
            val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, targetScaleY, scaleY)
            val set = AnimatorSet()
            set.playTogether(
                scaleXAnimator,
                scaleYAnimator,
                ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
            )
            return set
        }
        if (view.tag == "tv1" || view.tag == "tv2" || view.tag == "tv3") {

            val scaleX = startValues.values[PROPNAME_SCALE_X] as Float
            val scaleY = startValues.values[PROPNAME_SCALE_Y] as Float
            val transY = startValues.values[PROPNAME_TRANSLATE_Y] as Float

            val targetScaleX = endValues.values[PROPNAME_SCALE_X] as Float
            val targetScaleY = endValues.values[PROPNAME_SCALE_Y] as Float
            val targetTransY = endValues.values[PROPNAME_TRANSLATE_Y] as Float

            val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, targetScaleX, scaleX)
            val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, targetScaleY, scaleY)
            val translateYAnimator =
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, targetTransY, transY)
            val set = AnimatorSet()
            set.playTogether(
                scaleXAnimator,
                scaleYAnimator,
                translateYAnimator,
                ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
            )
            return set


        }
        return null
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        transitionValues.values[PROPNAME_SCALE_X] = transitionValues.view.scaleX
        transitionValues.values[PROPNAME_SCALE_Y] = transitionValues.view.scaleY
        transitionValues.values[PROPNAME_TRANSLATE_Y] = transitionValues.view.translationY
        Log.d("CustomTransition", "transitionValues: ${transitionValues}")
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        when (transitionValues.view.tag) {
            "tv1" -> {
                transitionValues.values[PROPNAME_SCALE_X] =
                    transitionValues.view.scaleX / 4
                transitionValues.values[PROPNAME_SCALE_Y] =
                    transitionValues.view.scaleY / 4
                // 获取屏幕高度
                val screenHeight = context.resources.displayMetrics.heightPixels
                transitionValues.values[PROPNAME_TRANSLATE_Y] = screenHeight.toFloat()
            }

            "tv2" -> {
                transitionValues.values[PROPNAME_SCALE_X] =
                    transitionValues.view.scaleX / 4
                transitionValues.values[PROPNAME_SCALE_Y] =
                    transitionValues.view.scaleY / 4
                // 获取屏幕高度
                val screenHeight = context.resources.displayMetrics.heightPixels
                transitionValues.values[PROPNAME_TRANSLATE_Y] = screenHeight.toFloat()
            }

            "tv3" -> {
                transitionValues.values[PROPNAME_SCALE_X] =
                    transitionValues.view.scaleX / 20
                transitionValues.values[PROPNAME_SCALE_Y] =
                    transitionValues.view.scaleY / 20
                // 获取屏幕高度
                val screenHeight = context.resources.displayMetrics.heightPixels
                transitionValues.values[PROPNAME_TRANSLATE_Y] = screenHeight.toFloat()
            }
            "background" -> {
                transitionValues.values[PROPNAME_SCALE_X] =
                    transitionValues.view.scaleX / 8
                transitionValues.values[PROPNAME_SCALE_Y] =
                    transitionValues.view.scaleY / 8
                // 获取屏幕高度
                val screenHeight = context.resources.displayMetrics.heightPixels
                transitionValues.view.pivotY = screenHeight.toFloat()
            }
        }

        Log.d("CustomTransition", "transitionValues: ${transitionValues}")
    }

}