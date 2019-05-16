package com.codercoral.observer

import android.content.Context
import android.util.Log
import com.codercoral.progress.ObserverOnNextListener

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class DMObserver<T>(private val context: Context, private val listener: ObserverOnNextListener<T>) : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        Log.d(TAG, "onSubscribe: ")
        //添加业务处理
    }

    override fun onNext(t: T) {
        listener.onNext(t)
    }

    override fun onError(e: Throwable) {
        Log.e(TAG, "onError: ", e)
        //添加业务处理
    }

    override fun onComplete() {
        Log.d(TAG, "onComplete: ")
        //添加业务处理
    }

    companion object {
        private val TAG = "MyObserver"
    }
}