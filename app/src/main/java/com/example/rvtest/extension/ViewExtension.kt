package com.example.rvtest.extension

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleOrGone(isVisible: Boolean) {
    if (isVisible) visible() else gone()
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    if (isVisible) visible() else invisible()
}