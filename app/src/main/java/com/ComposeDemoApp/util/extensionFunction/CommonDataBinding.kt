package com.ComposeDemoApp.util.extensionFunction

import android.annotation.SuppressLint
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Abhin.
 */

fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()
    defaultValue?.let {
        data.value = it
    }
    return data
}

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

@SuppressLint("CheckResult")
fun <T> ImageView.setPicture(
    obj: T,
    options: RequestOptions? = null,
    listener: RequestListener<Drawable>? = null,
    placeholder: Int? = null
) {
    Glide.with(this).load(obj).also {
        placeholder?.let { it1 -> it.placeholder(it1) }
        it.apply(
            options ?: RequestOptions.centerCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        if (listener != null)
            it.listener(listener)
        it.into(this)
    }
}

@SuppressLint("CheckResult")
fun <T> ImageView.setIcon(obj: T) {
    Glide.with(this).load(obj).also {
        it.apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
        it.into(this)
    }
}

@SuppressLint("CheckResult")
fun <T> ImageView.setBitmap(obj: T) {
    Glide.with(this).load(obj).also {
        it.apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
        it.into(this)
    }
}

@BindingAdapter("setVisible")
fun setVisible(layout: View?, show: Boolean?) {
    if (layout != null && show != null && show) {
        layout.show()
    } else {
        layout?.hide()
    }
}

@BindingAdapter("setHtmlText")
fun setHtmlText(
    view: AppCompatTextView,
    text: String?
) {
    view.setHtmlText = text.toString()
}


@BindingAdapter("setProfileUrl")
fun setProfileUrl(image: AppCompatImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        image.setPicture(url)
    }
}
