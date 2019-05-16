package com.codercoral.electricitychart.video.codercoral.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.codercoral.electricitychart.R
import com.codercoral.electricitychart.base.GlideApp
import jp.wasabeef.glide.transformations.BlurTransformation

object GlideUtil {
    //glide相关设置
    var options: RequestOptions = RequestOptions()
            .placeholder(R.mipmap.loading)
            .fitCenter()



    fun loadImg(activity: Context, url: String, imageView: ImageView) {
        GlideApp.with(activity)
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions().crossFade(500))
                .into(imageView)
    }


    fun loadvagueImg(activity: Context, url: String, imageView: ImageView) {
        GlideApp.with(activity)
                .load(url)
                .apply(options)
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .apply(bitmapTransform(BlurTransformation(23,8)))
                .transition(DrawableTransitionOptions().crossFade(500))
                .into(imageView)
    }
}