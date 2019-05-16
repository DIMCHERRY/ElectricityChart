package com.codercoral.electricitychart.video.codercoral.http

import com.codercoral.electricitychart.video.codercoral.videos.bean.VideoListBean
import com.codercoral.observer.DMObserver
import com.codercoral.progress.ProgressObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object ClientMethods {

    /**
     * 封装线程管理和订阅的过程
     */

    fun <T> apiSubscribe(observable: Observable<T>, observer: ProgressObserver<T>) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
    fun <T> apiSubscribe(observable: Observable<T>, observer: DMObserver<T>) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    //获取视频列表
    fun getVideoList(observer: ProgressObserver<VideoListBean>, page: Int) {
        apiSubscribe(Client.getClient(3).getVideoList(page), observer)
    }

    //加载更多视频
    fun getVideoLoadmore(observer: DMObserver<VideoListBean>, url:String) {
        apiSubscribe(Client.getClient(1).getMoreVideoList(url), observer)
    }
    //搜索视频
    fun getsearchVideo(observer: DMObserver<VideoListBean>, name:String) {
        apiSubscribe(Client.getClient(3).search(name,0,16), observer)
    }
}
