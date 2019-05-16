package com.codercoral.electricitychart.video.codercoral.videos.presenter

import com.codercoral.observer.DMObserver
import com.codercoral.electricitychart.video.codercoral.base.BaseActivity
import com.codercoral.electricitychart.video.codercoral.http.ClientMethods
import com.codercoral.electricitychart.video.codercoral.videos.bean.VideoListBean
import com.codercoral.progress.ObserverOnNextListener
import com.codercoral.progress.ProgressObserver

class VideoPresenter(private val view: VideoContract.view, private val context: BaseActivity) : VideoContract.presenter {

    override fun getVideoList(num: Int) {
        val listener = object : ObserverOnNextListener<VideoListBean> {
            override fun onNext(t: VideoListBean) {
                if (t.itemList.size != 0 && t.itemList != null) {
                    view.showData(t)
                } else {
                    view.showfailemsg("")
                }
            }
        }
        ClientMethods.getVideoList(ProgressObserver(listener, view), num)
    }

    override fun loadMoreList(url: String) {
        val listener = object : ObserverOnNextListener<VideoListBean> {
            override fun onNext(t: VideoListBean) {
                if (t != null) {
                    view.showMoreList(t)
                }else{
                    view.showfailemsg("")
                }
            }
        }
        ClientMethods.getVideoLoadmore(DMObserver(context, listener), url)
    }

    override fun searchVideos(name: String) {
        val listener = object : ObserverOnNextListener<VideoListBean> {
            override fun onNext(t: VideoListBean) {
                view.showSearch(t)
            }
        }
        ClientMethods.getsearchVideo(DMObserver(context,listener),name)
    }
}