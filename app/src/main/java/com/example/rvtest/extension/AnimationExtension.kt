package com.example.rvtest.extension

import android.view.animation.Animation

fun Animation.onAnimationEnd(onAnimationEnd: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            onAnimationEnd()
        }

        override fun onAnimationRepeat(p0: Animation?) {}
    })
}