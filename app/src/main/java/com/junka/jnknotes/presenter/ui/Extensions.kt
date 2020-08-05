package com.junka.jnknotes.presenter.ui

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observer(liveData: LiveData<T>, observer: (T) -> Unit){
    liveData.observe(this, Observer (observer))
}


fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}