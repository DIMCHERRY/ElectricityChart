package com.codercoral.progress

import android.util.Log
import com.codercoral.electricitychart.video.codercoral.base.RefreshContract
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ProgressObserver<T>(private val listener: ObserverOnNextListener<T>, refreshListener : RefreshContract) : Observer<T>,
    ProgressCancelListener {
    private var d: Disposable? = null
    private var newsListener : RefreshContract? = null

    init {
        this.newsListener = refreshListener
    }


    private fun showProgressDialog() {

    }

    private fun dismissProgressDialog() {
    }

    override fun onSubscribe(d: Disposable) {
        this.d = d
        newsListener!!.getLists(true)
    }

    override fun onNext(t: T) {
        listener.onNext(t)
    }

    override fun onError(e: Throwable) {
        newsListener!!.getLists(false)
        Log.e(TAG, "onError: ", e)
    }

    override fun onComplete() {
        newsListener!!.getLists(false)
        Log.d(TAG, "onComplete: ")
    }

    override fun onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d!!.isDisposed) {
            d!!.dispose()
        }
    }

    companion object {
        private val TAG = "ProgressObserver"
    }
}
