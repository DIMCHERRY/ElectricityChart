package com.codercoral.electricitychart.video.codercoral.http

import com.codercoral.electricitychart.video.codercoral.videos.bean.VideoListBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ClientService {

    //获取视频集合
    @GET("v4/discovery/hot")
    fun getVideoList(@Query("pn") page: Int): Observable<VideoListBean>

    //获取更多视频
    @GET
    fun getMoreVideoList(@Url url:String): Observable<VideoListBean>

    //获取搜索视频集合
    @GET("/api/v1/search")
    fun search(@Query("query") query: String,
               @Query("start") start: Int,
               @Query("num") num: Int): Observable<VideoListBean>
}
