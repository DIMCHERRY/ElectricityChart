package com.codercoral.electricitychart.video.codercoral.videos.presenter

import com.codercoral.electricitychart.video.codercoral.base.RefreshContract
import com.codercoral.electricitychart.video.codercoral.videos.bean.VideoListBean

interface VideoContract {
    interface view : RefreshContract {
        fun showData(videolist: VideoListBean)
        fun showfailemsg(str: String)
        override fun getLists(isshow: Boolean)
        fun showMoreList(videolist: VideoListBean)
        fun showSearch(videolist: VideoListBean)
    }

    interface presenter {
        fun getVideoList(num:Int)
        fun loadMoreList(url:String)
        fun searchVideos(name:String)
    }
}